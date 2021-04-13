package com.krzem.pi_calculation_leibinz_formula;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.Exception;
import java.util.ArrayList;



public class Main{
	public static void main(String[] args){
		new Main();
	}



	private boolean _file=false;



	public Main(){
		String[] fr=this._arctan_1_x_4("infinity");
		System.out.println(this._div_fr(fr[0],fr[1],"1000"));
	}



	private String[] _arctan_1_x_4(String nf){
		String n="1";
		String dn="1";
		String i="1";
		while (this._smaller(i,nf)){
			long st=System.nanoTime();
			String v=this._add(this._mult(i,"2"),"1");
			String[] fr=null;
			if (this._is_zero(this._modulo(i,"2"))==true){
				fr=this._add_fr(n,dn,"1",v);
			}
			else{
				fr=this._sub_fr(n,dn,"1",v);
			}
			n=fr[0];
			dn=fr[1];
			this._dump(i,this._mult(n,"4"),dn);
			double dff=(double)(System.nanoTime()-st);
			System.out.printf("STEP #%s (%.3f s):\t%s\n",i,dff*1e-9,this._div_fr(this._mult(n,"4"),dn,"100"));
			i=this._add(i,"1");
		}
		return new String[]{this._mult(n,"4"),dn};
	}



	private String[] _add_fr(String n1,String dn1,String n2,String dn2){
		return new String[]{this._add(this._mult(n1,dn2),this._mult(n2,dn1)),this._mult(dn1,dn2)};
	}



	private String[] _sub_fr(String n1,String dn1,String n2,String dn2){
		return new String[]{this._sub(this._mult(n1,dn2),this._mult(n2,dn1)),this._mult(dn1,dn2)};
	}



	private String _div_fr(String a,String b,String c){
		a+=".";
		for (String i="0";this._smaller(i,c);i=this._add(i,"1")){
			a+="0";
		}
		String o="";
		String dv="";
		for (int i=0;i<a.length();i++){
			if (a.charAt(i)=='.'){
				o+=".";
			}
			else{
				dv+=String.valueOf(a.charAt(i));
				if (this._smaller(dv,b)==true){
					o+="0";
				}
				else{
					String dn="0";
					while (!this._smaller(dv,b)){
						dv=this._sub(dv,b);
						dn=this._add(dn,"1");
					}
					o+=dn;
				}
			}
		}
		while (o.length()>=2&&o.charAt(0)=='0'){
			o=o.substring(1);
		}
		return o;
	}



	private boolean _is_zero(String n){
		return n.equals("0");
	}



	private boolean _smaller(String a,String b){
		if (b.equals("infinity")){
			return true;
		}
		while (a.length()>=2&&a.charAt(0)=='0'){
			a=a.substring(1);
		}
		while (b.length()>=2&&b.charAt(0)=='0'){
			b=b.substring(1);
		}
		if (a.length()<b.length()){
			return true;
		}
		if (a.length()>b.length()){
			return false;
		}
		for (int i=0;i<a.length();i++){
			int na=Integer.parseInt(String.valueOf(a.charAt(i)));
			int nb=Integer.parseInt(String.valueOf(b.charAt(i)));
			if (na<nb){
				return true;
			}
			if (na>nb){
				return false;
			}
		}
		return false;
	}



	private String _modulo(String a,String b){
		return this._sub(a,this._mult(this._div(a,b),b));
	}



	private String _div(String a,String b){
		String o="";
		String dv="";
		for (int i=0;i<a.length();i++){
			dv+=String.valueOf(a.charAt(i));
			if (this._smaller(dv,b)==true){
				o+="0";
			}
			else{
				String dn="0";
				while (!this._smaller(dv,b)){
					dv=this._sub(dv,b);
					dn=this._add(dn,"1");
				}
				o+=dn;
			}
		}
		while (o.length()>=2&&o.charAt(0)=='0'){
			o=o.substring(1);
		}
		return o;
	}



	private String _sub(String a,String b){/////////////////////////////////
		String o="";
		while (a.length()<b.length()){
			a="0"+a;
		}
		while (a.length()>b.length()){
			b="0"+b;
		}
		int s=0;
		for (int i=a.length()-1;i>=0;i--){
			int na=Integer.parseInt(String.valueOf(a.charAt(i)))-s;
			int nb=Integer.parseInt(String.valueOf(b.charAt(i)));
			s=0;
			if (nb>na){
				na+=10;
				s=1;
			}
			o=Integer.toString(na-nb)+o;
		}
		while (o.length()>=2&&o.charAt(0)=='0'){
			o=o.substring(1);
		}
		return o;
	}



	private String _mult(String a,String b){
		String to="0";
		for (int mi=b.length()-1;mi>=0;mi--){
			int mt=Integer.parseInt(String.valueOf(b.charAt(mi)));
			String o="";
			int p=0;
			for (int i=a.length()-1;i>=0;i--){
				int n=Integer.parseInt(String.valueOf(a.charAt(i)));
				o=Integer.toString((n*mt+p)%10)+o;
				p=(n*mt+p)/10;
			}
			if (p>0){
				o=Integer.toString(p)+o;
			}
			for (int i=0;i<b.length()-1-mi;i++){
				o+="0";
			}
			to=this._add(to,o);
		}
		return to;
	}



	private String _add(String a,String b){
		String o="";
		while (a.length()<b.length()){
			a="0"+a;
		}
		while (a.length()>b.length()){
			b="0"+b;
		}
		int p=0;
		for (int i=a.length()-1;i>=0;i--){
			int na=Integer.parseInt(String.valueOf(a.charAt(i)));
			int nb=Integer.parseInt(String.valueOf(b.charAt(i)));
			o=Integer.toString((na+nb+p)%10)+o;
			p=(na+nb+p)/10;
		}
		if (p>0){
			o=Integer.toString(p)+o;
		}
		return o;
	}



	private void _dump(String i,String n,String dn){
		if (this._file==true){
			return;
		}
		this._file=true;
		Main cls=this;
		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					BufferedWriter w=new BufferedWriter(new FileWriter("dump.txt"));
					w.write(String.format("%s\n\n\n%s\n\n\n%s",i,n,dn));
					w.close();
					cls._file=false;
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}
}
