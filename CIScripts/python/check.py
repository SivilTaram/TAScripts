import os
import sys


def check_structure(dir_path):
    # check if exist `src`
    src_full = os.path.join(dir_path, "src")
    if not os.path.exists(src_full):
        raise AssertionError("没有 src 文件夹!")

    # check if exist `bin`
    bin_full = os.path.join(dir_path, "bin")
    if not os.path.exists(bin_full):
        raise AssertionError("没有 bin 文件夹!")

    # check is exist `main.py`
    main_full = os.path.join(dir_path, "bin", "main.py")
    if not os.path.exists(main_full):
        raise AssertionError("没有 main.py 文件!")


if __name__ == '__main__':
    check_dir = sys.argv[1]
    log_file = open(sys.argv[2], "w", encoding="utf8")
    try:
        check_structure(check_dir)
        log_file.write("文件目录测试通过 (3/3)")
        sys.exit(0)
    except AssertionError as e:
        log_file.write(str(e))
        sys.exit(-1)
    finally:
        log_file.close()
