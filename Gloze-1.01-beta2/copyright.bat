@echo off
rem *** Update copyright and email headers ***

for /R %%f in (*.java *.txt) do (
echo %%f
copy %%f temp.sed >> log.sed
sed "s/Hewlett-Packard Company 2001 - 20../Hewlett-Packard Company 2001 - 2009/; s/steve\.battle@hp.com/steven\.a\.battle@googlemail.com/" temp.sed > %%f
del temp.sed
)
del log.sed
