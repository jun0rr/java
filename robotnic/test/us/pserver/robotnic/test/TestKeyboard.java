/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic.test;

import us.pserver.robotnic.Key;
import us.pserver.robotnic.Keyboard;
import static us.pserver.robotnic.Keyboard.*;
import us.pserver.robotnic.RepeatingCombo;
import us.pserver.robotnic.Robotnic;
import us.pserver.robotnic.ScriptCombo;


/**
 *
 * @author juno
 */
public class TestKeyboard {
  
  //abcdefghijklmnopqrstuvwxyz
  //ABCDEFGHIJKLMNOPQRSTUVWXYZ
  //0123456789
  //0123456789
  //áâàãäéêèẽëíîìĩïóôòõöúûùũüýŷỳỹÿ
  //ÁÂÀÃÄÉÊÈẼËÍÎÌĨÏÓÔÒÕÖÚÛÙŨÜÝŶỲỸŸ
  
  public static void main(String[] args) {
    Robotnic r = Robotnic.getDefault();
    r.delay(2000);
    ScriptCombo nl = ScriptCombo.of(Keyboard.ENTER, RepeatingCombo.of(Key.SLASH, 2));
    ScriptCombo a2z = ScriptCombo.of(
        A_LOWER, B_LOWER, C_LOWER, 
        D_LOWER, E_LOWER, F_LOWER, 
        G_LOWER, H_LOWER, I_LOWER, 
        J_LOWER, K_LOWER, L_LOWER, 
        M_LOWER, N_LOWER, O_LOWER, 
        P_LOWER, Q_LOWER, R_LOWER, 
        S_LOWER, T_LOWER, U_LOWER, 
        V_LOWER, W_LOWER, X_LOWER, 
        Y_LOWER, Z_LOWER
    );
    a2z.accept(r);
    nl.accept(r);
    ScriptCombo A2Z = ScriptCombo.of(
        A_UPPER, B_UPPER, C_UPPER, 
        D_UPPER, E_UPPER, F_UPPER, 
        G_UPPER, H_UPPER, I_UPPER, 
        J_UPPER, K_UPPER, L_UPPER, 
        M_UPPER, N_UPPER, O_UPPER, 
        P_UPPER, Q_UPPER, R_UPPER, 
        S_UPPER, T_UPPER, U_UPPER, 
        V_UPPER, W_UPPER, X_UPPER, 
        Y_UPPER, Z_UPPER
    );
    A2Z.accept(r);
    nl.accept(r);
    ScriptCombo numpad = ScriptCombo.of(
        NUMPAD_0, NUMPAD_1, 
        NUMPAD_2, NUMPAD_3, 
        NUMPAD_4, NUMPAD_5, 
        NUMPAD_6, NUMPAD_7, 
        NUMPAD_8, NUMPAD_9
    );
    numpad.accept(r);
    nl.accept(r);
    ScriptCombo nums = ScriptCombo.of(_0, _1, _2, _3, _4, _5, _6, _7, _8, _9);
    nums.accept(r);
    nl.accept(r);
    ScriptCombo acentos = ScriptCombo.of(
        A_ACUTE_LOWER, A_CIRC_LOWER, 
        A_GRAVE_LOWER, A_TILDE_LOWER, 
        A_UMBRA_LOWER, E_ACUTE_LOWER, 
        E_CIRC_LOWER, E_GRAVE_LOWER, 
        E_TILDE_LOWER, E_UMBRA_LOWER, 
        I_ACUTE_LOWER, I_CIRC_LOWER, 
        I_GRAVE_LOWER, I_TILDE_LOWER, 
        I_UMBRA_LOWER, O_ACUTE_LOWER, 
        O_CIRC_LOWER, O_GRAVE_LOWER, 
        O_TILDE_LOWER, O_UMBRA_LOWER, 
        U_ACUTE_LOWER, U_CIRC_LOWER, 
        U_GRAVE_LOWER, U_TILDE_LOWER, 
        U_UMBRA_LOWER, Y_ACUTE_LOWER, 
        Y_CIRC_LOWER, Y_GRAVE_LOWER, 
        Y_TILDE_LOWER, Y_UMBRA_LOWER
    );
    acentos.accept(r);
    nl.accept(r);
    ScriptCombo acentosUpper = ScriptCombo.of(
        A_ACUTE_UPPER, A_CIRC_UPPER, 
        A_GRAVE_UPPER, A_TILDE_UPPER, 
        A_UMBRA_UPPER, E_ACUTE_UPPER, 
        E_CIRC_UPPER, E_GRAVE_UPPER, 
        E_TILDE_UPPER, E_UMBRA_UPPER, 
        I_ACUTE_UPPER, I_CIRC_UPPER, 
        I_GRAVE_UPPER, I_TILDE_UPPER, 
        I_UMBRA_UPPER, O_ACUTE_UPPER, 
        O_CIRC_UPPER, O_GRAVE_UPPER, 
        O_TILDE_UPPER, O_UMBRA_UPPER, 
        U_ACUTE_UPPER, U_CIRC_UPPER, 
        U_GRAVE_UPPER, U_TILDE_UPPER, 
        U_UMBRA_UPPER, Y_ACUTE_UPPER, 
        Y_CIRC_UPPER, Y_GRAVE_UPPER, 
        Y_TILDE_UPPER, Y_UMBRA_UPPER
    );
    acentosUpper.accept(r);
  }
  
}
