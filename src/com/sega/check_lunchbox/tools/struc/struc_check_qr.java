package com.sega.check_lunchbox.tools.struc;

public class struc_check_qr
{
	public boolean result;
	public String msg_err, universidad, nombre, siglas, diciplina, branch;
	
	public struc_check_qr()
	{
		this.result = false;
		this.msg_err = "";
		this.siglas = "";
		this.universidad = "";
		this.diciplina = "";
		this.branch = "";
		this.nombre = "";
	}
	
	public struc_check_qr(boolean result, String msg_err, 
			String siglas, String univercidad, String diciplina, 
			String branch, String nombre)
	{
		this.result = result;
		this.msg_err = msg_err;
		this.siglas = siglas;
		this.universidad = univercidad;
		this.diciplina = diciplina;
		this.branch = branch;
		this.nombre = nombre;
	}
}
