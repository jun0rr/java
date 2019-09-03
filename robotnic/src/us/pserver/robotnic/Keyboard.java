/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.awt.event.KeyEvent;


/**
 *
 * @author juno
 */
public enum Keyboard implements Key {
  
  _0(KeyEvent.VK_0, '0'),
  _1(KeyEvent.VK_1, '1'),
  _2(KeyEvent.VK_2, '2'),
  _3(KeyEvent.VK_3, '3'),
  _4(KeyEvent.VK_4, '4'),
  _5(KeyEvent.VK_5, '5'),
  _6(KeyEvent.VK_6, '6'),
  _7(KeyEvent.VK_7, '7'),
  _8(KeyEvent.VK_8, '8'),
  _9(KeyEvent.VK_9, '9'),
  
  A(KeyEvent.VK_A, 'a'),
  A_UPPER(KeyEvent.VK_A, 'A'),
  A_GRAVE('à', 'à'),
  A_GRAVE_UPPER('À', 'À'),
  A_ACUTE('á', 'á'),
  A_ACUTE_UPPER('Á', 'Á'),
  A_CIRC('â', 'â'),
  A_CIRC_UPPER('Â', 'Â'),
  A_TILDE('ã', 'ã'),
  A_TILDE_UPPER('Ã', 'Ã'),
  A_UMBRA('ä', 'ä'),
  A_UMBRA_UPPER('Ä', 'Ä'),
  ACUTE(KeyEvent.VK_DEAD_ACUTE, '\''),
  ALT(KeyEvent.VK_ALT, "ALT"),
  ALT_GRAPH(KeyEvent.VK_ALT_GRAPH, "ALTGR"),
  AMPERSAND(KeyEvent.VK_AMPERSAND, '&'),
  ARROW_DOWN(KeyEvent.VK_DOWN, '↓'),
  ARROW_LEFT(KeyEvent.VK_LEFT, '←'),
  ARROW_RIGHT(KeyEvent.VK_RIGHT, '→'),
  ARROW_UP(KeyEvent.VK_UP, '↑'),
  ASTERISC(KeyEvent.VK_ASTERISK, '*'),
  AT(KeyEvent.VK_AT, '@'),
  
  B(KeyEvent.VK_B, 'b'),
  B_UPPER(KeyEvent.VK_B, 'B'),
  BACKSLASH(KeyEvent.VK_BACK_SLASH, '\\'),
  BACKSPACE(KeyEvent.VK_BACK_SPACE, "BACKSPACE"),
  BRACELEFT(KeyEvent.VK_BRACELEFT, '['),
  BRACERIGHT(KeyEvent.VK_BRACERIGHT, ']'),
  
  C(KeyEvent.VK_C, 'c'),
  C_UPPER(KeyEvent.VK_C, 'C'),
  CAPSLOCK(KeyEvent.VK_CAPS_LOCK, "CAPSLOCK"),
  CLOSE_CURLY_BRACE(KeyEvent.VK_BRACERIGHT, '}'),
  CLOSE_BRACKET(KeyEvent.VK_CLOSE_BRACKET, ']'),
  CLOSE_PARENTHESIS(KeyEvent.VK_RIGHT_PARENTHESIS, ')'),
  COLON(KeyEvent.VK_COLON, ':'),
  COMMA(KeyEvent.VK_COMMA, ','),
  COPYRIGHT('©', '©'),
  CTRL(KeyEvent.VK_CONTROL, "CTRL"),
  CCEDIL(KeyEvent.VK_DEAD_CEDILLA, 'ç'),
  
  D(KeyEvent.VK_D, 'd'),
  D_UPPER(KeyEvent.VK_D, 'U'),
  DELETE(KeyEvent.VK_DELETE, "DEL"),
  DOLLAR(KeyEvent.VK_DOLLAR, '$'),
  
  E(KeyEvent.VK_E, 'e'),
  E_UPPER(KeyEvent.VK_E, 'E'),
  E_GRAVE('è', 'è'),
  E_GRAVE_UPPER('È', 'È'),
  E_ACUTE('é', 'é'),
  E_ACUTE_UPPER('É', 'É'),
  E_CIRC_UPPER('Ê', 'Ê'),
  E_CIRC_LOWER('ê', 'ê'),
  E_TILDE_UPPER('Ẽ', 'Ẽ'),
  E_TILDE_LOWER('ẽ', 'ẽ'),
  E_UMBRA_UPPER('Ë', 'Ë'),
  E_UMBRA_LOWER('ë', 'ë'),
  END(KeyEvent.VK_END, "END"),
  ENTER(KeyEvent.VK_ENTER, "ENTER"),
  EQUALS(KeyEvent.VK_EQUALS, '='),
  ESCAPE(KeyEvent.VK_ESCAPE, '?'),
  EUR('€', '€'),
  EXCLAMATION(KeyEvent.VK_EXCLAMATION_MARK, '!'),
  
  F(KeyEvent.VK_F, 'f'),
  F_UPPER(KeyEvent.VK_F, 'F'),
  F1(KeyEvent.VK_F1, "F1"),
  F2(KeyEvent.VK_F2, "F2"),
  F3(KeyEvent.VK_F3, "F3"),
  F4(KeyEvent.VK_F4, "F4"),
  F5(KeyEvent.VK_F5, "F5"),
  F6(KeyEvent.VK_F6, "F6"),
  F7(KeyEvent.VK_F7, "F7"),
  F8(KeyEvent.VK_F8, "F8"),
  F9(KeyEvent.VK_F9, "F9"),
  F10(KeyEvent.VK_F10, "F10"),
  F11(KeyEvent.VK_F11, "F11"),
  F12(KeyEvent.VK_F12, "F12"),
  
  G(KeyEvent.VK_G, 'g'),
  G_UPPER(KeyEvent.VK_G, 'G'),
  GRAVE(KeyEvent.VK_DEAD_GRAVE, '>'),
  GREATER(KeyEvent.VK_GREATER, '>'),
  
  H(KeyEvent.VK_H, 'h'),
  H_UPPER(KeyEvent.VK_H, 'H'),
  HOME(KeyEvent.VK_HOME, "HOME"),
  
  I(KeyEvent.VK_I, 'i'),
  I_UPPER(KeyEvent.VK_I, 'I'),
  I_GRAVE('ì', 'ì'),
  I_GRAVE_UPPER('Ì', 'Ì'),
  I_ACUTE('í', 'í'),
  I_ACUTE_UPPER('Í', 'Í'),
  I_CIRC('î', 'î'),
  I_CIRC_UPPER('Î', 'Î'),
  I_TILDE('ĩ', 'ĩ'),
  I_TILDE_UPPER('Ĩ', 'Ĩ'),
  I_UMBRA('ï', 'ï'),
  I_UMBRA_UPPER('Ï', 'Ï'),
  INSERT(KeyEvent.VK_INSERT, 'i'),
  
  J(KeyEvent.VK_J, 'j'),
  J_UPPER(KeyEvent.VK_J, 'J'),
  
  K(KeyEvent.VK_K, 'k'),
  K_UPPER(KeyEvent.VK_K, 'K'),
  
  L(KeyEvent.VK_L, 'l'),
  L_UPPER(KeyEvent.VK_L, 'L'),
  LEFTDBL('«', '«'),
  
  M(KeyEvent.VK_M, 'm'),
  M_UPPER(KeyEvent.VK_M, 'M'),
  
  MINUS(KeyEvent.VK_MINUS, '-'),
  
  N(KeyEvent.VK_N, 'n'),
  N_UPPER(KeyEvent.VK_N, 'N'),
  NUM_LOCK(KeyEvent.VK_NUM_LOCK, "NUM_LOCK"),
  
  NUMPAD_0(KeyEvent.VK_NUMPAD0, '0'),
  NUMPAD_1(KeyEvent.VK_NUMPAD1, '1'),
  NUMPAD_2(KeyEvent.VK_NUMPAD2, '2'),
  NUMPAD_3(KeyEvent.VK_NUMPAD3, '3'),
  NUMPAD_4(KeyEvent.VK_NUMPAD4, '4'),
  NUMPAD_5(KeyEvent.VK_NUMPAD5, '5'),
  NUMPAD_6(KeyEvent.VK_NUMPAD6, '6'),
  NUMPAD_7(KeyEvent.VK_NUMPAD7, '7'),
  NUMPAD_8(KeyEvent.VK_NUMPAD8, '8'),
  NUMPAD_9(KeyEvent.VK_NUMPAD9, '9'),
  
  O(KeyEvent.VK_O, 'o'),
  O_UPPER(KeyEvent.VK_O, 'O'),
  O_GRAVE('ò', 'ò'),
  O_GRAVE_UPPER('Ò', 'Ò'),
  O_ACUTE('ó', 'ó'),
  O_ACUTE_UPPER('Ó', 'Ó'),
  O_CIRC('ô', 'ô'),
  O_CIRC_UPPER('Ô', 'Ô'),
  O_TILDE('õ', 'õ'),
  O_TILDE_UPPER('Õ', 'Õ'),
  O_UMBRA('ö', 'ö'),
  O_UMBRA_UPPER('Ö', 'Ö'),
  OPEN_CURLY_BRACE(KeyEvent.VK_BRACELEFT, '{'),
  OPEN_BRACKET(KeyEvent.VK_OPEN_BRACKET, '['),
  OPEN_PARENTHESIS(KeyEvent.VK_RIGHT_PARENTHESIS, '('),
  
  P(KeyEvent.VK_P, 'p'),
  P_UPPER(KeyEvent.VK_P, 'P'),
  PG_DOWN(KeyEvent.VK_PAGE_DOWN, "PG_DOWN"),
  PG_UP(KeyEvent.VK_PAGE_UP, "PG_UP"),
  PERIOD(KeyEvent.VK_PERIOD, '.'),
  PLUS(KeyEvent.VK_PLUS, '+'),
  POUND('£', '£'),
  PRINTSCREEN(KeyEvent.VK_PRINTSCREEN, "PRINTSCREEN"),
  
  Q(KeyEvent.VK_Q, 'q'),
  Q_UPPER(KeyEvent.VK_Q, 'Q'),
  QUOTE(KeyEvent.VK_QUOTE, '\''),
  QUOTEDBL(KeyEvent.VK_QUOTEDBL, '"'),
  
  R(KeyEvent.VK_R, 'r'),
  R_UPPER(KeyEvent.VK_R, 'R'),
  RIGHTDBL('»', '»'),
  REGISTERED('®', '®'),
  
  S(KeyEvent.VK_S, 's'),
  S_UPPER(KeyEvent.VK_S, 'S'),
  SCROLL_LOCK(KeyEvent.VK_SCROLL_LOCK, "SCROLL_LOCK"),
  SEMICOLON(KeyEvent.VK_SEMICOLON, ';'),
  SHIFT(KeyEvent.VK_SHIFT, "SHIFT"),
  SLASH(KeyEvent.VK_SLASH, '/'),
  SPACE(KeyEvent.VK_SPACE, ' '),
  SUPER_1('¹', '¹'),
  SUPER_2('²', '²'),
  SUPER_3('³', '³'),
  
  T(KeyEvent.VK_T, 't'),
  T_UPPER(KeyEvent.VK_T, 'T'),
  TILDE(KeyEvent.VK_DEAD_TILDE, '~'),
  TAB(KeyEvent.VK_TAB, "TAB"),
  
  U(KeyEvent.VK_U, 'u'),
  U_UPPER(KeyEvent.VK_U, 'U'),
  U_GRAVE('ù', 'ù'),
  U_GRAVE_UPPER('Ù', 'Ù'),
  U_ACUTE('ú', 'ú'),
  U_ACUTE_UPPER('Ú', 'Ú'),
  U_CIRC('û', 'û'),
  U_CIRC_UPPER('Û', 'Û'),
  U_TILDE('ũ', 'ũ'),
  U_TILDE_UPPER('Ũ', 'Ũ'),
  U_UMBRA('ü', 'ü'),
  U_UMBRA_UPPER('Ü', 'Ü'),
  
  UNDERSCORE(KeyEvent.VK_UNDERSCORE, '_'),
  
  V(KeyEvent.VK_V, 'v'),
  V_UPEER(KeyEvent.VK_V, 'V'),
  
  W(KeyEvent.VK_W, 'w'),
  W_UPPER(KeyEvent.VK_W, 'W'),
  WIN(KeyEvent.VK_WINDOWS, "WIN"),
  
  X(KeyEvent.VK_X, 'x'),
  X_UPPER(KeyEvent.VK_X, 'X'),
  
  Y(KeyEvent.VK_Y, 'y'),
  Y_UPPER(KeyEvent.VK_Y, 'Y'),
  Y_GRAVE('ỳ', 'ỳ'),
  Y_GRAVE_UPPER('Ỳ', 'Ỳ'),
  Y_ACUTE('ý', 'ý'),
  Y_ACUTE_UPPER('Ý', 'Ý'),
  Y_CIRC('ŷ', 'ŷ'),
  Y_CIRC_UPPER('Ŷ', 'Ŷ'),
  Y_TILDE('ỹ', 'ỹ'),
  Y_TILDE_UPPER('Ỹ', 'Ỹ'),
  Y_UMBRA('ÿ', 'ÿ'),
  Y_UMBRA_UPPER('Ÿ', 'Ÿ'),

  Z(KeyEvent.VK_Z, 'z'),
  Z_UPPER(KeyEvent.VK_Z, 'Z'),
  ;
  
  private Keyboard(int cd, char ch) {
    this.code = cd;
    this.ch = ch;
    this.str = Character.toString(ch);
  }
  
  private Keyboard(int cd, String str) {
    this.code = cd;
    this.ch = 0;
    this.str = str;
  }
  
  private final int code;
  
  private final char ch;
  
  private final String str;
  
  @Override
  public int getCode() {
    return code;
  }
  
  @Override
  public char getChar() {
    return ch;
  }
  
  @Override
  public String toString() {
    return str;
  }
  
}
