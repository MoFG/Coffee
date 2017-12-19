/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Administrator
 */
public class checkSymbolK {
    public static char[] syb = {'!','@','#','$','%','^','&','*',':',';','+','_','-','='};
    public static Boolean checkSb(String s)
    {
            boolean b = false;
            char a[] = s.toCharArray();
            
            for(int i =0; i<a.length;i++)
            {
                for(int j=0; j<syb.length;j++)
                {
                    if(a[i]==syb[j])
                        b= true;
                }
            }
            return b;
    }
}
