package com.sega.check_lunchbox.tools.struc;

public class struc_Entradas
{
	public int normal;
	public int boxlunch;
	
	public struc_Entradas()
	{
		boxlunch = 0;
		normal = 0;
	}
	
	public struc_Entradas(int boxlunch, int normal)
	{
		this.boxlunch = boxlunch;
		this.normal = normal;
	}
}
