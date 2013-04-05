package com.sega.check_lunchbox.tools.struc;

public class struc_Login
{
	public int id;
	public String nombre;
	public boolean sportman, comitiva, judge, staff;
	
	public struc_Login()
	{
		id = 0;
		nombre = "";
		sportman = comitiva = judge = staff = false;
	}
	
	public struc_Login(int id, String nombre, boolean sportman, boolean comitiva,
			boolean judge, boolean staff)
	{
		this.id = id;
		this.nombre = nombre;
		this.sportman = sportman;
		this.comitiva = comitiva;
		this.judge = judge;
		this.staff = staff;
	}

}
