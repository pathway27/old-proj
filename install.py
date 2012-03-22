import os
os.system('dir | awk "{ print $5 }" | grep "ME" > dlcs.txt')

read = open('dlcs.txt', 'r')
#title ME2_AltAppearance1.exe
#echo Installing %1ME2_AltAppearance1.exe
#%1ME2_AltAppearance1.exe

for line in read:
	print('Installing: ' + line)
	os.system(line.strip() + ' | ' + 'done.exe' + '\n')
	
	
read.close()
#os.system('giveme2entitlements.exe')
os.system('giveme2entitlements_v2.exe')
#os.system('install.bat')
os.system('del dlcs.txt')
#os.system('del install.bat')
# APPLY CRACK Soon./