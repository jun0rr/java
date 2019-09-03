/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.util.Optional;
import java.util.stream.Stream;


/**
 *
 * @author Juno
 */
public enum Keyboard implements Script {
  
  A_LOWER('a', 
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.A)
  ),
  A_UPPER('A', 
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.A)
  ),
  A_ACUTE_LOWER('á', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.ACUTE, Key.A)
  ),
  A_ACUTE_UPPER('Á', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.ACUTE, Key.A)
  ),
  A_CIRC_LOWER('â', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.A)
  ),
  A_CIRC_UPPER('Â', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.A)
  ),
  A_GRAVE_LOWER('à', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.A)
  ),
  A_GRAVE_UPPER('À', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.A)
  ),
  A_TILDE_LOWER('ã', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.TILDE, Key.A)
  ),
  A_TILDE_UPPER('Ã', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.TILDE, Key.A)
  ),
  A_UMBRA_LOWER('ä', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.A)
  ),
  A_UMBRA_UPPER('Ä', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.A)
  ),
  ACUTE('\'', KeyCombo.type(Key.ACUTE, Key.SPACE)),
  AMPERSAND('&', KeyCombo.press(Key.SHIFT, Key._7)),
  ARROW_DOWN('↓', KeyAction.type(Key.ARROW_DOWN)),
  ARROW_LEFT('←', KeyAction.type(Key.ARROW_LEFT)),
  ARROW_RIGHT('→', KeyAction.type(Key.ARROW_RIGHT)),
  ARROW_UP('↑', KeyAction.type(Key.ARROW_UP)),
  ASTERISC('*', KeyCombo.press(Key.SHIFT, Key._8)),
  AT('@', KeyCombo.press(Key.SHIFT, Key._2)),
  
  B_LOWER('b', 
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.B)
  ),
  B_UPPER('B', 
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.B)
  ),
  BACKSLASH('\\', KeyAction.type(Key.BACKSLASH)),
  BACKSPACE('\b', KeyAction.type(Key.BACKSPACE)),
  BRACELEFT('[', KeyAction.type(Key.BRACELEFT)),
  BRACERIGHT(']', KeyAction.type(Key.BRACERIGHT)),
  
  C_LOWER('c', 
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.C)
  ),
  C_UPPER('C', 
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.C)
  ),
  CCEDIL_LOWER('ç', 
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.CCEDIL)
  ),
  CCEDIL_UPPER('ç', 
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.CCEDIL)
  ),
  CIRC('^', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.SPACE)
  ),
  COLON(':', KeyAction.type(Key.COLON)),
  COMMA(',', KeyAction.type(Key.COMMA)),
  COPYRIGHT('©', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_6, Key.NUMPAD_9), KeyAction.release(Key.SHIFT)) : KeyCombo.press(Key.ALTGR, Key.C)),
  CURLY_BRACE_LEFT('{', KeyCombo.press(Key.SHIFT, Key.BRACELEFT)),
  CURLY_BRACE_RIGHT('}', KeyCombo.press(Key.SHIFT, Key.BRACERIGHT)),

  D_LOWER('d',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.D)
  ),
  D_UPPER('D',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.D)
  ),
  DEGREES('°', KeyCombo.press(Key.ALTGR, Key.SLASH)),
  DIVISION('÷', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_2, Key.NUMPAD_4, Key.NUMPAD_7), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key.PERIOD)),
  DOLLAR('$', KeyCombo.press(Key.SHIFT, Key._4)),

  E_LOWER('e',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.E)
  ),
  E_UPPER('E',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.E)
  ),
  E_GRAVE_LOWER('è',
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.E)
  ),
  E_GRAVE_UPPER('È',
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.E)
  ),
  E_ACUTE_LOWER('é', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.ACUTE, Key.E)
  ),
  E_ACUTE_UPPER('É', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.ACUTE, Key.E)
  ),
  E_CIRC_LOWER('ê', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.E)
  ),
  E_CIRC_UPPER('Ê', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.E)
  ),
  E_TILDE_LOWER('ẽ', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.TILDE, Key.E)
  ),
  E_TILDE_UPPER('Ẽ', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.TILDE, Key.E)
  ),
  E_UMBRA_LOWER('ë', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.E)
  ),
  E_UMBRA_UPPER('Ë', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.E)
  ),
  ENTER('\n', KeyAction.type(Key.ENTER)),
  EQUALS('=', KeyAction.type(Key.EQUALS)),
  EUR('€', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_2, Key.NUMPAD_8), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.E)),
  EXCLAMATION('!', KeyCombo.press(Key.SHIFT, Key._1)),

  F_LOWER('f',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.F)
  ),
  F_UPPER('F',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.F)
  ),
  FRACTION_1_2('½', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_8, Key.NUMPAD_9), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key._2)),
  FRACTION_3_4('¾', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_9, Key.NUMPAD_0), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key._3)),
  FRACTION_1_4('¼', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_8, Key.NUMPAD_8), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key._4)),

  G_LOWER('g',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.G)
  ),
  G_UPPER('G',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.G)
  ),
  GRAVE('`', KeyCombo.press(Key.SHIFT, Key.GRAVE), KeyAction.type(Key.SPACE)),
  GREATER('>', KeyCombo.press(Key.SHIFT, Key.PERIOD)),
  
  H_LOWER('h',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.H)
  ),
  H_UPPER('H',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.H)
  ),
  
  I_LOWER('i',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.I)
  ),
  I_UPPER('I',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.I)
  ),
  I_GRAVE_LOWER('ì',
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.I)
  ),
  I_GRAVE_UPPER('Ì',
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.I)
  ),
  I_ACUTE_LOWER('í', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.ACUTE, Key.I)
  ),
  I_ACUTE_UPPER('Í', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.ACUTE, Key.I)
  ),
  I_CIRC_LOWER('î', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.I)
  ),
  I_CIRC_UPPER('Î', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.I)
  ),
  I_TILDE_LOWER('ĩ', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.TILDE, Key.I)
  ),
  I_TILDE_UPPER('Ĩ', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.TILDE, Key.I)
  ),
  I_UMBRA_LOWER('ï', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.I)
  ),
  I_UMBRA_UPPER('Ï', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.I)
  ),

  J_LOWER('j',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.J)
  ),
  J_UPPER('J',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.J)
  ),
  
  K_LOWER('k',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.K)
  ),
  K_UPPER('K',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.K)
  ),
  
  L_LOWER('l',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.L)
  ),
  L_UPPER('L',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.L)
  ),
  LEFTDBL('«', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_7, Key.NUMPAD_1), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.Z)),
  LESSER('<', KeyCombo.press(Key.SHIFT, Key.COMMA)),
  
  M_LOWER('m',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.M)
  ),
  M_UPPER('M',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.M)
  ),
  MINUS('-', KeyAction.type(Key.MINUS)),
  MORE_OR_LESS('±', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_7, Key.NUMPAD_7), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key._9)),
  MULTIPLY('×', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_2, Key.NUMPAD_1, Key.NUMPAD_5), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key.COMMA)),
  
  N_LOWER('n',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.N)
  ),
  N_UPPER('N',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.N)
  ),
  NUMPAD_0('0', r->OS.setNumLockEnabled(true), KeyAction.type(Key.NUMPAD_0)),
  NUMPAD_1('1', r->OS.setNumLockEnabled(true), KeyAction.type(Key.NUMPAD_1)),
  NUMPAD_2('2', r->OS.setNumLockEnabled(true), KeyAction.type(Key.NUMPAD_2)),
  NUMPAD_3('3', r->OS.setNumLockEnabled(true), KeyAction.type(Key.NUMPAD_3)),
  NUMPAD_4('4', r->OS.setNumLockEnabled(true), KeyAction.type(Key.NUMPAD_4)),
  NUMPAD_5('5', r->OS.setNumLockEnabled(true), KeyAction.type(Key.NUMPAD_5)),
  NUMPAD_6('6', r->OS.setNumLockEnabled(true), KeyAction.type(Key.NUMPAD_6)),
  NUMPAD_7('7', r->OS.setNumLockEnabled(true), KeyAction.type(Key.NUMPAD_7)),
  NUMPAD_8('8', r->OS.setNumLockEnabled(true), KeyAction.type(Key.NUMPAD_8)),
  NUMPAD_9('9', r->OS.setNumLockEnabled(true), KeyAction.type(Key.NUMPAD_9)),
  
  O_LOWER('o',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.O)
  ),
  O_UPPER('O',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.O)
  ),
  O_GRAVE_LOWER('ò',
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.O)
  ),
  O_GRAVE_UPPER('Ò',
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.O)
  ),
  O_ACUTE_LOWER('ó', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.ACUTE, Key.O)
  ),
  O_ACUTE_UPPER('Ó', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.ACUTE, Key.O)
  ),
  O_CIRC_LOWER('ô', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.O)
  ),
  O_CIRC_UPPER('Ô', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.O)
  ),
  O_TILDE_LOWER('õ', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.TILDE, Key.O)
  ),
  O_TILDE_UPPER('Ô', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.TILDE, Key.O)
  ),
  O_UMBRA_LOWER('ö', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.O)
  ),
  O_UMBRA_UPPER('Ö', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.O)
  ),
  
  P_LOWER('p',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.P)
  ),
  P_UPPER('P',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.P)
  ),
  PARAGRAPH('─', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_5, Key.NUMPAD_1), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.COMMA)),
  PARENTHESIS_LEFT('(', KeyCombo.press(Key.SHIFT, Key._9)),  
  PARENTHESIS_RIGHT(')', KeyCombo.press(Key.SHIFT, Key._0)),  
  PERCENT('%', KeyCombo.press(Key.SHIFT, Key._5)),
  PERIOD('.', KeyAction.type(Key.PERIOD)),
  PIPE('|', KeyCombo.press(Key.SHIFT, Key.BACKSLASH)),
  PLUS('+', KeyCombo.press(Key.SHIFT, Key.EQUALS)),
  POUND('£', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_6, Key.NUMPAD_3), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key._4)),
  
  Q_LOWER('q',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.Q)
  ),
  Q_UPPER('Q',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.Q)
  ),
  QUOTE('\'', KeyAction.type(Key.QUOTE)),
  QUOTEDBL('"', KeyAction.type(Key.QUOTEDBL)),
  
  R_LOWER('r',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.R)
  ),
  R_UPPER('R',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.R)
  ),
  REGISTERED('®', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_7, Key.NUMPAD_4), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.R)),
  RIGHTDBL('»', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_8, Key.NUMPAD_7), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.X)),

  S_LOWER('s',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.S)
  ),
  S_UPPER('S',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.S)
  ),
  SEMICOLON(';', KeyAction.type(Key.SEMICOLON)),
  SHARP('#', KeyCombo.press(Key.SHIFT, Key._3)),
  SLASH('/', KeyAction.type(Key.SLASH)),
  SPACE(' ', KeyAction.type(Key.SPACE)),
  SUPER_1('¹', KeyCombo.press(Key.ALTGR, Key._1)),
  SUPER_2('²', KeyCombo.press(Key.ALTGR, Key._2)),
  SUPER_3('³', KeyCombo.press(Key.ALTGR, Key._3)),
  SYMBOL_LEFT('←', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_2, Key.NUMPAD_7), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.Y)),
  SYMBOL_UP('↑', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_2, Key.NUMPAD_4), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key.U)),
  SYMBOL_RIGHT('→', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_2, Key.NUMPAD_6), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.I)),
  SYMBOL_DOWN('↓', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_2, Key.NUMPAD_5), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.I)),

  T_LOWER('t',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.T)
  ),
  T_UPPER('T',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.T)
  ),
  TAB('\t', KeyAction.type(Key.TAB)),
  TILDE('~', KeyAction.type(Key.TILDE), KeyAction.type(Key.SPACE)),
  TM('™', OS.isWindows() ? new ScriptCombo(KeyAction.press(Key.ALT), NumpadCombo.type(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_5, Key.NUMPAD_3), KeyAction.release(Key.ALT)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key._8)),

  U_LOWER('u',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.U)
  ),
  U_UPPER('U',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.U)
  ),
  U_GRAVE_LOWER('ù',
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.U)
  ),
  U_GRAVE_UPPER('Ù',
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.U)
  ),
  U_ACUTE_LOWER('ú', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.ACUTE, Key.U)
  ),
  U_ACUTE_UPPER('Ú', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.ACUTE, Key.U)
  ),
  U_CIRC_LOWER('û', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.U)
  ),
  U_CIRC_UPPER('Û', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.U)
  ),
  U_TILDE_LOWER('ũ', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.TILDE, Key.U)
  ),
  U_TILDE_UPPER('Ũ', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.TILDE, Key.U)
  ),
  U_UMBRA_LOWER('ü', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.U)
  ),
  U_UMBRA_UPPER('Ü', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.U)
  ),
  UNDERSCORE('_', KeyCombo.press(Key.SHIFT, Key.MINUS)),

  V_LOWER('v',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.V)
  ),
  V_UPPER('V',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.V)
  ),

  W_LOWER('w',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.W)
  ),
  W_UPPER('W',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.W)
  ),

  X_LOWER('x',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.X)
  ),
  X_UPPER('X',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.X)
  ),

  Y_LOWER('y',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.Y)
  ),
  Y_UPPER('Y',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.Y)
  ),
  Y_GRAVE_LOWER('ỳ',
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.Y)
  ),
  Y_GRAVE_UPPER('Ỳ',
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.Y)
  ),
  Y_ACUTE_LOWER('ý', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.ACUTE, Key.Y)
  ),
  Y_ACUTE_UPPER('Ý', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.ACUTE, Key.Y)
  ),
  Y_CIRC_LOWER('ŷ', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.Y)
  ),
  Y_CIRC_UPPER('Ŷ', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.Y)
  ),
  Y_TILDE_LOWER('ỹ', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.type(Key.TILDE, Key.Y)
  ),
  Y_TILDE_UPPER('Ỹ', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.type(Key.TILDE, Key.Y)
  ),
  Y_UMBRA_LOWER('ÿ', 
      r->OS.setCapsLockEnabled(false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.Y)
  ),
  Y_UMBRA_UPPER('Ÿ', 
      r->OS.setCapsLockEnabled(true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.Y)
  ),

  Z_LOWER('z',
      r->OS.setCapsLockEnabled(false), 
      KeyAction.type(Key.Z)
  ),
  Z_UPPER('Z',
      r->OS.setCapsLockEnabled(true), 
      KeyAction.type(Key.Z)
  ),

  INSERT((char)-1, KeyAction.type(Key.INSERT)),
  HOME((char)-2, KeyAction.type(Key.HOME)),
  PAGE_UP((char)-3, KeyAction.type(Key.PG_UP)),
  DELETE((char)-4, KeyAction.type(Key.DELETE)),
  END((char)-5, KeyAction.type(Key.END)),
  PAGE_DOWN((char)-6, KeyAction.type(Key.PG_DOWN)),
  
  SHIFT((char)-10, KeyAction.type(Key.SHIFT)),
  CONTROL((char)-11, KeyAction.type(Key.CTRL)),
  ALT((char)-12, KeyAction.type(Key.ALT)),
  ALTGR((char)-13, KeyAction.type(Key.ALTGR)),
  
  NUM_LOCK((char)-15, KeyAction.type(Key.NUM_LOCK)),
  NUM_LOCK_ENABLED((char)-16, r -> OS.setNumLockEnabled(true)),
  NUM_LOCK_DISABLED((char)-17, r -> OS.setNumLockEnabled(false)),
  SCROLL_LOCK((char)-18, KeyAction.type(Key.SCROLL_LOCK)),
  SCROLL_LOCK_ENABLED((char)-19, r -> OS.setScrollLockEnabled(true)),
  SCROLL_LOCK_DISABLED((char)-20, r -> OS.setScrollLockEnabled(false)),
  CAPS_LOCK((char)-21, KeyAction.type(Key.CAPSLOCK)),
  CAPS_LOCK_ENABLED((char)-22, r -> OS.setCapsLockEnabled(true)),
  CAPS_LOCK_DISABLED((char)-23, r -> OS.setCapsLockEnabled(false)),
  PRINT_SCREEN((char)-24, KeyAction.type(Key.PRINTSCREEN)),
  ESCAPE((char)-26, KeyAction.type(Key.ESCAPE)),
  WINDOWS((char)-27, KeyAction.type(Key.WIN)),

  COPY((char)-28, KeyCombo.press(Key.CTRL, Key.C)),
  CUT((char)-29, KeyCombo.press(Key.CTRL, Key.X)),
  PASTE((char)-30, KeyCombo.press(Key.CTRL, Key.V)),
  UNDO((char)-31, KeyCombo.press(Key.CTRL, Key.Z));
  ;
  
  private Keyboard(char c, Script... ss) {
    this.c = c;
    this.script = new ScriptCombo(ss);
  }
  
  private final char c;
  
  private final Script script;
  
  public char getChar() {
    return c;
  }
  
  @Override
  public void exec(Robotnic r) {
    this.script.exec(r);
  }
  
  
  public static Optional<Keyboard> getByChar(char c) {
    return Stream.of(Keyboard.values()).filter(k -> k.getChar() == c).findAny();
  }
  
}
