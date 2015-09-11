@echo off

@rem open the first skype account
start "" "C:\Program Files (x86)\Skype\Phone\Skype.exe" /secondary /username:dharmen_panchal /password:<pw>

@rem hold on for 6 seconds
ping 127.0.0.1 -n 6 > nul

@rem open the second skype account
start "" "C:\Program Files (x86)\Skype\Phone\Skype.exe" /secondary /username:dharmen.panchal.azilen /password:<pw>

exit