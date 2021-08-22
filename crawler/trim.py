import os
import re
import io


top_dir = "E:\code_completing\data\shim\\"

def trim_dir(path):
    print("目录：" + path)
    for root, dirs, files in os.walk(path):
        for name in files:
            trim_file(os.path.join(root, name))



def trim_file(path):
    print("文件：" + path)
    
    if re.match(".*js", path):
        print("处理")
    else:
        print("忽略")
        return
    
        
    bak_file = path + ".bak";
    os.rename(path, bak_file);
    
    fp_src = open(bak_file);
    fp_dst = open(path, 'w', encoding="utf-8");
    
    for line in fp_src.readlines():
        if line:
            line = line.encode("utf-8", "ignore")
            fp_dst.write(str(line.decode('utf-8','strict')))
        else:
            break
    
    fp_src.close()
    fp_dst.close()
    os.remove(bak_file)



trim_dir(top_dir)