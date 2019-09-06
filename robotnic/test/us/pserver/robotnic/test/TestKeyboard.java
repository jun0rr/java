/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic.test;

import us.pserver.robotnic.Key;
import us.pserver.robotnic.Keyboard;
import static us.pserver.robotnic.Keyboard.A_LOWER;
import static us.pserver.robotnic.Keyboard.A_UPPER;
import static us.pserver.robotnic.Keyboard.B_LOWER;
import static us.pserver.robotnic.Keyboard.B_UPPER;
import static us.pserver.robotnic.Keyboard.C_LOWER;
import static us.pserver.robotnic.Keyboard.C_UPPER;
import static us.pserver.robotnic.Keyboard.D_LOWER;
import static us.pserver.robotnic.Keyboard.D_UPPER;
import static us.pserver.robotnic.Keyboard.E_LOWER;
import static us.pserver.robotnic.Keyboard.E_UPPER;
import static us.pserver.robotnic.Keyboard.F_LOWER;
import static us.pserver.robotnic.Keyboard.F_UPPER;
import static us.pserver.robotnic.Keyboard.G_LOWER;
import static us.pserver.robotnic.Keyboard.G_UPPER;
import static us.pserver.robotnic.Keyboard.H_LOWER;
import static us.pserver.robotnic.Keyboard.H_UPPER;
import static us.pserver.robotnic.Keyboard.I_LOWER;
import static us.pserver.robotnic.Keyboard.I_UPPER;
import static us.pserver.robotnic.Keyboard.J_LOWER;
import static us.pserver.robotnic.Keyboard.J_UPPER;
import static us.pserver.robotnic.Keyboard.K_LOWER;
import static us.pserver.robotnic.Keyboard.K_UPPER;
import static us.pserver.robotnic.Keyboard.L_LOWER;
import static us.pserver.robotnic.Keyboard.L_UPPER;
import static us.pserver.robotnic.Keyboard.M_LOWER;
import static us.pserver.robotnic.Keyboard.M_UPPER;
import static us.pserver.robotnic.Keyboard.N_LOWER;
import static us.pserver.robotnic.Keyboard.N_UPPER;
import static us.pserver.robotnic.Keyboard.O_LOWER;
import static us.pserver.robotnic.Keyboard.O_UPPER;
import static us.pserver.robotnic.Keyboard.P_LOWER;
import static us.pserver.robotnic.Keyboard.P_UPPER;
import static us.pserver.robotnic.Keyboard.Q_LOWER;
import static us.pserver.robotnic.Keyboard.Q_UPPER;
import static us.pserver.robotnic.Keyboard.R_LOWER;
import static us.pserver.robotnic.Keyboard.R_UPPER;
import static us.pserver.robotnic.Keyboard.S_LOWER;
import static us.pserver.robotnic.Keyboard.S_UPPER;
import static us.pserver.robotnic.Keyboard.T_LOWER;
import static us.pserver.robotnic.Keyboard.T_UPPER;
import static us.pserver.robotnic.Keyboard.U_LOWER;
import static us.pserver.robotnic.Keyboard.U_UPPER;
import static us.pserver.robotnic.Keyboard.V_LOWER;
import static us.pserver.robotnic.Keyboard.V_UPPER;
import static us.pserver.robotnic.Keyboard.W_LOWER;
import static us.pserver.robotnic.Keyboard.W_UPPER;
import static us.pserver.robotnic.Keyboard.X_LOWER;
import static us.pserver.robotnic.Keyboard.X_UPPER;
import static us.pserver.robotnic.Keyboard.Y_LOWER;
import static us.pserver.robotnic.Keyboard.Y_UPPER;
import static us.pserver.robotnic.Keyboard.Z_LOWER;
import static us.pserver.robotnic.Keyboard.Z_UPPER;
import us.pserver.robotnic.RepeatingCombo;
import us.pserver.robotnic.Robotnic;
import us.pserver.robotnic.ScriptCombo;


/**
 *
 * @author juno
 */
public class TestKeyboard {
  
  //
  
  public static void main(String[] args) {
    Robotnic r = Robotnic.getDefault();
    ScriptCombo nl = ScriptCombo.of(Keyboard.ENTER, RepeatingCombo.of(Key.SLASH, 2));
    ScriptCombo a2z = ScriptCombo.of(A_LOWER, B_LOWER, C_LOWER, D_LOWER, E_LOWER, F_LOWER, G_LOWER, H_LOWER, I_LOWER, J_LOWER, K_LOWER, L_LOWER, M_LOWER, N_LOWER, O_LOWER, P_LOWER, Q_LOWER, R_LOWER, S_LOWER, T_LOWER, U_LOWER, V_LOWER, W_LOWER, X_LOWER, Y_LOWER, Z_LOWER);
    r.delay(2000);
    a2z.exec(r);
    nl.exec(r);
    ScriptCombo A2Z = ScriptCombo.of(A_UPPER, B_UPPER, C_UPPER, D_UPPER, E_UPPER, F_UPPER, G_UPPER, H_UPPER, I_UPPER, J_UPPER, K_UPPER, L_UPPER, M_UPPER, N_UPPER, O_UPPER, P_UPPER, Q_UPPER, R_UPPER, S_UPPER, T_UPPER, U_UPPER, V_UPPER, W_UPPER, X_UPPER, Y_UPPER, Z_UPPER);
    A2Z.exec(r);
    ScriptCombo nums = ScriptCombo.of(Keyboard.)
  }
  
}
