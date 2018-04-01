using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace GitAutoCloner
{
    class Program
    {
        //Need files
        public static string BlogFile = @"./blog.txt";

        //Gen directories
        public static string LogDir = @"./Log";
        public static string ProjectDir = @"./Projects";

        //Gen files
        public static string RepoFile = @"./RepoMap.txt";

        //For single mode
        public static string Number;

        public static string Pattern = "https://git.coding.net/.+?\\.git";

        public static string ModeInput = "a";

        static void Main(string[] args)
        {

            try
            {
                if (args.Length > 0)
                {
                    for (int i = 1; i < args.Length; i += 2)
                    {
                        switch (args[i - 1])
                        {
                            case "-blog":
                                BlogFile = args[i];
                                break;
                            case "-mode":
                                ModeInput = args[i];
                                break;
                            case "-num":
                                Number = args[i];
                                break;
                            case "-pat":
                                Pattern = args[i];
                                break;
                        }
                    }
                }
                CheckFileStatus();
                GrabRepos();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                Console.WriteLine(e.StackTrace);
                Hint();
                Console.Read();
            }
        }

        public static void Hint()
        {
            Console.WriteLine("Usages: \n" +
                              "autoclone.exe -blog [blog file] -mode [mode] -num [number id] -pat [git pattern]\n\n" +
                              "\t- 本功能用于从学生的作业中自动提取Github链接, 并将Git仓库Clone到文件夹 Projects 下, 错误日志在文件夹 Log 下, Github项目链接映射表在 RepoMap.txt 中。\n\n" +
                              "\t- 文件 [blog file] 提供学号与作业地址的对应关系, 多行分开。如不指定该参数则默认为当前目录 BlogList.txt。\n\t 每行的格式如: 031502334\thttp://cnblogs.com/easteast/p/1234.html 【分隔符为\\t】\n\n" +
                              "\t- 仓库模式 [git pattern] 提供仓库地址的正则表达式, 默认为克隆 Github, 样例: https://git.coding.net/.+\\.git" +
                              "\t- 文本 [mode] 指定测试选用的模式，目前提供三种选择：\n\t\t- a : 跳过当前 Projects目录下已有工程, 分析其他同学的博客 ,并将项目克隆到本地。\n\t\t- w : 将 Projects 文件夹重命名,爬取所有学生的博客并将项目克隆到 Projects文件夹下。\n\n" +
                              "\t- 学号 [number id] 提供单个学号, 当本参数存在时, 将只抓取单个同学的博客并重新克隆工程。\n\n");
        }

        public static void CheckFileStatus()
        {
            ReCreateDir(LogDir);
            //Grab 所有同学的博客并克隆项目
            if (!Directory.Exists(ProjectDir))
            {
                //如果没有 Projects 目录, 就创建一个
                Directory.CreateDirectory(ProjectDir);
                //单个测试的情况下
                if (Number != null)
                {
                    //如果已经有了项目目录, 就删除它
                    var clonePath = Path.Combine(ProjectDir, Number);
                    RemoveDir(clonePath);
                }
            }
            else if (Number == null
                        && ModeInput.Equals(Mode.Written)
                        && Directory.Exists(ProjectDir))
            {
                ReCreateDir(ProjectDir);
            }
        }

        //Overview: 间接删除目录 dirPath
        //Require: 只能用于带学号场景
        public static void RemoveDir(string dirPath)
        {
            if (Directory.Exists(dirPath))
            {
                TimeSpan ts = DateTime.UtcNow - new DateTime(1970, 1, 1, 0, 0, 0, 0);
                var timeStr = Convert.ToInt64(ts.TotalSeconds).ToString();
                string newPath = Path.Combine(ProjectDir, Number + "-" + timeStr);
                Directory.Move(dirPath, newPath);
            }
        }

       
        //分析博客,自动获取Github仓库链接并存储至指定目录
        public static void GrabRepos()
        {
            GitRepoHandler handler = new GitRepoHandler(ProjectDir);
            //集体测试模式
            if (Number == null)
            {
                //把每个学生的Github Repo都存储在文件夹BASE_DIR下,并以学号命名文件夹
                Console.WriteLine("开始爬取所有学生的博客并匹配生成Github项目目录 ...");
                handler.GetAllGithubRepos();
            }
            //独立测试模式
            else
            {
                //仅抓取并克隆一个同学的博客
                Console.WriteLine($"开始爬取 {Number} 的博客并匹配生成Github项目目录 ...");
                handler.GetOneGithubRepo(Number);
            }
        }

        //Overview: 重新生成目录 dirPath
        public static void ReCreateDir(string dirPath)
        {
            if (Directory.Exists(dirPath))
            {
                if (Directory.GetDirectories(dirPath).Any() || Directory.GetFiles(dirPath).Any())
                {
                    TimeSpan ts = DateTime.UtcNow - new DateTime(1970, 1, 1, 0, 0, 0, 0);
                    var timeStr = Convert.ToInt64(ts.TotalSeconds).ToString();
                    string newPath = dirPath + "-" + timeStr;
                    Directory.Move(dirPath, newPath);
                }
                else
                {
                    Directory.Delete(dirPath);
                }
            }
            Directory.CreateDirectory(dirPath);
        }

    }
}
