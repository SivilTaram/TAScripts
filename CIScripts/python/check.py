import os
import sys

def check_structure(dir_path):
    # check if exist `src`
    src_full = os.path.join(dir_path, "src")
    if not os.path.exists(src_full):
        return "没有 src 文件夹!"

    # check if exist `bin`
    bin_full = os.path.join(dir_path, "bin")
    if not os.path.exists(bin_full):
        return "没有 bin 文件夹!"

    # check is exist `main.py`
    main_full = os.path.join(dir_path, "bin", "main.py")
    if not os.path.exists(main_full):
        return "没有 main.py 文件!"

    return "文件目录确认完毕 (3/3)"


if __name__ == '__main__':
    check_dir = sys.argv[1]
    print(check_structure(check_dir))
