package com.example.mapsexample;

import android.content.Context;

public class AdicionaInstitutos {
	
	public static void addAll(Context context) {
		InstitutoDatabaseHandler instDB = new InstitutoDatabaseHandler(context);
		
		/*if(!instDB.isEmpty())
			return;*/
		
		Instituto[] i = new Instituto[2];
		i[0] = new Instituto("IME", -23.558952, -46.731141, "Matemática", "IME - MAT, MAE, MAP, DCC", ECategory.EXATAS, R.drawable.ic_launcher);
		i[1] = new Instituto("FEA", -23.559109, -46.728587, "Economia", "FEA - Econimia, ADM, Contabilidade", ECategory.HUMANAS, R.drawable.ic_launcher);
		
		for (Instituto instituto : i) {
			instDB.insert(instituto);
		}
	}

}
