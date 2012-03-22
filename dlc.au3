WinWaitActive("Mass")
Send("{ENTER}")
If ControlGetText("Mass", "", 1037) == "Confirm Installation Directory" Then
	Send("{ENTER}")
EndIf
WinWaitActive("Mass", "Completed")
Send("{ENTER}")
WinWaitActive("Mass", "Comp", 5)
Send("{ENTER}")