'''
Author: scikkk 203536673@qq.com
Date: 2022-11-22 23:13:34
LastEditors: scikkk
LastEditTime: 2022-11-23 00:06:12
Description: file content
'''
import random
from tqdm import tqdm
outfile=open('random.txt','w')
for i in tqdm(range(1000000)):
    rdnum=random.randint(-5000000,5000000)
    outfile.write(str(rdnum)+' ')
outfile.close()
