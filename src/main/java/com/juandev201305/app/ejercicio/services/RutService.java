package com.juandev201305.app.ejercicio.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * SERVICE RUT:
 * Implementación de logica para verificar ruts legitimos.
 */
@Service
public class RutService {
    public String verificarRut(String rutStr){
        ArrayList<Integer> rut = new ArrayList<Integer>();
        Integer dv;
        if(rutStr.toLowerCase().charAt(rutStr.length()-1)=='k'){
            dv = 10;
        } else {
            dv = Character.getNumericValue(rutStr.charAt(rutStr.length()-1));
        }

        for(int i=0;i<rutStr.length()-1;i++){
            rut.add(Character.getNumericValue(rutStr.charAt(i)));
            System.out.println(rut);
        }
        int total=0,multiplicador=2;
        for(int i = rut.size()-1; i >= 0; i--){
            total+=rut.get(i)*multiplicador;
            System.out.println(rut.get(i)+"*"+multiplicador);
            multiplicador++;
            if(multiplicador==8){
                multiplicador=2;
            }
        }
        System.out.println(total);
        int division = total/11;
        System.out.println(division);
        division*=11;
        System.out.println(division);
        total-=division;
        System.out.println(total);
        int verificador=11-total;
        System.out.println(verificador+ " "+dv);
        if(verificador == 11){
            verificador = 0;
        }
        if(dv!=verificador){
            return null;
        }
        return rutStr;
    }
}
