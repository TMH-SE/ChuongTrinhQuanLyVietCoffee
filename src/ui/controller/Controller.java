/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import javafx.fxml.Initializable;

/**
 *
 * @author HUU
 */
public interface Controller extends Initializable{
    default public boolean getPassState(){
        return true;
    }
}
