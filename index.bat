echo off
echo NUL>_.class&&del /s /f /q *.class
cls
javac com/krzem/pi_calculation_leibinz_formula/Main.java&&java com/krzem/pi_calculation_leibinz_formula/Main
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"