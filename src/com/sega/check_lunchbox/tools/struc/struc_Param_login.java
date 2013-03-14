package com.sega.check_lunchbox.tools.struc;

public class struc_Param_login
{
	public String date, str_dinner_room;
	public int food, type_food, dinner_room;
	
	public struc_Param_login(String date, int food, int type_food, int dinner_room, String str_dinner_room)
	{
		this.date = date;
		this.food = food;
		this.type_food = type_food;
		this.dinner_room = dinner_room;
		this.str_dinner_room = str_dinner_room;
	}
}
