/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems.software;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author LiamF
 */
public class Member implements Serializable {
    public String userName;
    public String passWord;
    public  String dateOfBirth;
    public String placeOfBirth;
    public ArrayList<String> friends = new ArrayList<>();
    public ArrayList<String> prefMusic = new ArrayList<>();
}
