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
  
  _0('0', KeyAction.press(Key._0)),
  _1('1', KeyAction.press(Key._1)),
  _2('2', KeyAction.press(Key._2)),
  _3('3', KeyAction.press(Key._3)),
  _4('4', KeyAction.press(Key._4)),
  _5('5', KeyAction.press(Key._5)),
  _6('6', KeyAction.press(Key._6)),
  _0('7', KeyAction.press(Key._7)),
  _0('8', KeyAction.press(Key._8)),
  _0('9', KeyAction.press(Key._9)),
  
  A_LOWER('a', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.A)
  ),
  A_UPPER('A', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.A)
  ),
  A_ACUTE_LOWER('á', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.ACUTE, Key.A)
  ),
  A_ACUTE_UPPER('Á', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.ACUTE, Key.A)
  ),
  A_CIRC_LOWER('â', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.A)
  ),
  A_CIRC_UPPER('Â', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.A)
  ),
  A_GRAVE_LOWER('à', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.A)
  ),
  A_GRAVE_UPPER('À', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.A)
  ),
  A_TILDE_LOWER('ã', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.TILDE, Key.A)
  ),
  A_TILDE_UPPER('Ã', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.TILDE, Key.A)
  ),
  A_UMBRA_LOWER('ä', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.A)
  ),
  A_UMBRA_UPPER('Ä', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.A)
  ),
  ACUTE('\'', KeyCombo.type(Key.ACUTE, Key.SPACE)),
  AMPERSAND('&', KeyCombo.press(Key.SHIFT, Key._7)),
  //ARROW_DOWN('↓', KeyAction.type(Key.ARROW_DOWN)),
  //ARROW_LEFT('←', KeyAction.type(Key.ARROW_LEFT)),
  //ARROW_RIGHT('→', KeyAction.type(Key.ARROW_RIGHT)),
  //ARROW_UP('↑', KeyAction.type(Key.ARROW_UP)),
  ASTERISC('*', KeyCombo.press(Key.SHIFT, Key._8)),
  AT('@', KeyCombo.press(Key.SHIFT, Key._2)),
  
  B_LOWER('b', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.B)
  ),
  B_UPPER('B', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.B)
  ),
  //BACKSLASH('\\', KeyAction.type(Key.BACKSLASH)),
  //BACKSPACE('\b', KeyAction.type(Key.BACKSPACE)),
  BRACELEFT('[', KeyAction.type(Key.BRACELEFT)),
  BRACERIGHT(']', KeyAction.type(Key.BRACERIGHT)),
  
  C_LOWER('c', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.C)
  ),
  C_UPPER('C', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.C)
  ),
  CCEDIL_LOWER('ç', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.ACUTE, Key.C)
  ),
  CCEDIL_UPPER('ç', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.ACUTE, Key.C)
  ),
  CIRC('^', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.SPACE)
  ),
  COLON(':', KeyCombo.press(Key.SHIFT, Key.SEMICOLON)),
  COMMA(',', KeyAction.type(Key.COMMA)),
  COPYRIGHT('©', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_6, Key.NUMPAD_9)) : KeyCombo.press(Key.ALTGR, Key.C)),
  CURLY_BRACE_LEFT('{', KeyCombo.press(Key.SHIFT, Key.BRACELEFT)),
  CURLY_BRACE_RIGHT('}', KeyCombo.press(Key.SHIFT, Key.BRACERIGHT)),

  D_LOWER('d',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.D)
  ),
  D_UPPER('D',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.D)
  ),
  DEGREES('°', KeyCombo.press(Key.ALTGR, Key.SLASH)),
  DIVISION('÷', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_2, Key.NUMPAD_4, Key.NUMPAD_7)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key.PERIOD)),
  DOLLAR('$', KeyCombo.press(Key.SHIFT, Key._4)),

  E_LOWER('e',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.E)
  ),
  E_UPPER('E',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.E)
  ),
  E_GRAVE_LOWER('è',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.E)
  ),
  E_GRAVE_UPPER('È',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.E)
  ),
  E_ACUTE_LOWER('é', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.ACUTE, Key.E)
  ),
  E_ACUTE_UPPER('É', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.ACUTE, Key.E)
  ),
  E_CIRC_LOWER('ê', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.E)
  ),
  E_CIRC_UPPER('Ê', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.E)
  ),
  E_TILDE_LOWER('ẽ', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.TILDE, Key.E)
  ),
  E_TILDE_UPPER('Ẽ', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.TILDE, Key.E)
  ),
  E_UMBRA_LOWER('ë', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.E)
  ),
  E_UMBRA_UPPER('Ë', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.E)
  ),
  ENTER('\n', KeyAction.type(Key.ENTER)),
  EQUALS('=', KeyAction.type(Key.EQUALS)),
  EUR('€', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_2, Key.NUMPAD_8)) : KeyCombo.press(Key.ALTGR, Key.E)),
  EXCLAMATION('!', KeyCombo.press(Key.SHIFT, Key._1)),

  F_LOWER('f',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.F)
  ),
  F_UPPER('F',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.F)
  ),
  FRACTION_1_2('½', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_8, Key.NUMPAD_9)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key._2)),
  FRACTION_3_4('¾', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_9, Key.NUMPAD_0)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key._3)),
  FRACTION_1_4('¼', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_8, Key.NUMPAD_8)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key._4)),

  G_LOWER('g',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.G)
  ),
  G_UPPER('G',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.G)
  ),
  GRAVE('`', KeyCombo.press(Key.SHIFT, Key.GRAVE), KeyAction.type(Key.SPACE)),
  GREATER('>', KeyCombo.press(Key.SHIFT, Key.PERIOD)),
  
  H_LOWER('h',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.H)
  ),
  H_UPPER('H',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.H)
  ),
  
  I_LOWER('i',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.I)
  ),
  I_UPPER('I',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.I)
  ),
  I_GRAVE_LOWER('ì',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.I)
  ),
  I_GRAVE_UPPER('Ì',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.I)
  ),
  I_ACUTE_LOWER('í', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.ACUTE, Key.I)
  ),
  I_ACUTE_UPPER('Í', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.ACUTE, Key.I)
  ),
  I_CIRC_LOWER('î', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.I)
  ),
  I_CIRC_UPPER('Î', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.I)
  ),
  I_TILDE_LOWER('ĩ', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.TILDE, Key.I)
  ),
  I_TILDE_UPPER('Ĩ', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.TILDE, Key.I)
  ),
  I_UMBRA_LOWER('ï', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.I)
  ),
  I_UMBRA_UPPER('Ï', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.I)
  ),

  J_LOWER('j',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.J)
  ),
  J_UPPER('J',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.J)
  ),
  
  K_LOWER('k',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.K)
  ),
  K_UPPER('K',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.K)
  ),
  
  L_LOWER('l',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.L)
  ),
  L_UPPER('L',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.L)
  ),
  LEFTDBL('«', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_7, Key.NUMPAD_1)) : KeyCombo.press(Key.ALTGR, Key.Z)),
  LESSER('<', KeyCombo.press(Key.SHIFT, Key.COMMA)),
  
  M_LOWER('m',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.M)
  ),
  M_UPPER('M',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.M)
  ),
  MINUS('-', KeyAction.type(Key.MINUS)),
  MORE_OR_LESS('±', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_7, Key.NUMPAD_7)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key._9)),
  MULTIPLY('×', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_2, Key.NUMPAD_1, Key.NUMPAD_5)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key.COMMA)),
  
  N_LOWER('n',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.N)
  ),
  N_UPPER('N',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.N)
  ),
  NUMPAD_0('0', r->r.setKeyLockState(Key.NUM_LOCK, true), KeyAction.type(Key.NUMPAD_0)),
  NUMPAD_1('1', r->r.setKeyLockState(Key.NUM_LOCK, true), KeyAction.type(Key.NUMPAD_1)),
  NUMPAD_2('2', r->r.setKeyLockState(Key.NUM_LOCK, true), KeyAction.type(Key.NUMPAD_2)),
  NUMPAD_3('3', r->r.setKeyLockState(Key.NUM_LOCK, true), KeyAction.type(Key.NUMPAD_3)),
  NUMPAD_4('4', r->r.setKeyLockState(Key.NUM_LOCK, true), KeyAction.type(Key.NUMPAD_4)),
  NUMPAD_5('5', r->r.setKeyLockState(Key.NUM_LOCK, true), KeyAction.type(Key.NUMPAD_5)),
  NUMPAD_6('6', r->r.setKeyLockState(Key.NUM_LOCK, true), KeyAction.type(Key.NUMPAD_6)),
  NUMPAD_7('7', r->r.setKeyLockState(Key.NUM_LOCK, true), KeyAction.type(Key.NUMPAD_7)),
  NUMPAD_8('8', r->r.setKeyLockState(Key.NUM_LOCK, true), KeyAction.type(Key.NUMPAD_8)),
  NUMPAD_9('9', r->r.setKeyLockState(Key.NUM_LOCK, true), KeyAction.type(Key.NUMPAD_9)),
  
  O_LOWER('o',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.O)
  ),
  O_UPPER('O',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.O)
  ),
  O_GRAVE_LOWER('ò',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.O)
  ),
  O_GRAVE_UPPER('Ò',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.O)
  ),
  O_ACUTE_LOWER('ó', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.ACUTE, Key.O)
  ),
  O_ACUTE_UPPER('Ó', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.ACUTE, Key.O)
  ),
  O_CIRC_LOWER('ô', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.O)
  ),
  O_CIRC_UPPER('Ô', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.O)
  ),
  O_TILDE_LOWER('õ', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.TILDE, Key.O)
  ),
  O_TILDE_UPPER('Ô', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.TILDE, Key.O)
  ),
  O_UMBRA_LOWER('ö', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.O)
  ),
  O_UMBRA_UPPER('Ö', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.O)
  ),
  
  P_LOWER('p',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.P)
  ),
  P_UPPER('P',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.P)
  ),
  PARAGRAPH('─', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_5, Key.NUMPAD_1)) : KeyCombo.press(Key.ALTGR, Key.COMMA)),
  PARENTHESIS_LEFT('(', KeyCombo.press(Key.SHIFT, Key._9)),  
  PARENTHESIS_RIGHT(')', KeyCombo.press(Key.SHIFT, Key._0)),  
  PERCENT('%', KeyCombo.press(Key.SHIFT, Key._5)),
  PERIOD('.', KeyAction.type(Key.PERIOD)),
  PIPE('|', KeyCombo.press(Key.SHIFT, Key.BACKSLASH)),
  PLUS('+', KeyCombo.press(Key.SHIFT, Key.EQUALS)),
  POUND('£', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_6, Key.NUMPAD_3)) : KeyCombo.press(Key.ALTGR, Key._4)),
  
  Q_LOWER('q',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.Q)
  ),
  Q_UPPER('Q',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.Q)
  ),
  QUOTE('\'', KeyAction.type(Key.QUOTE)),
  QUOTEDBL('"', KeyAction.type(Key.QUOTEDBL)),
  
  R_LOWER('r',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.R)
  ),
  R_UPPER('R',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.R)
  ),
  REGISTERED('®', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_7, Key.NUMPAD_4)) : KeyCombo.press(Key.ALTGR, Key.R)),
  RIGHTDBL('»', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_8, Key.NUMPAD_7)) : KeyCombo.press(Key.ALTGR, Key.X)),

  S_LOWER('s',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.S)
  ),
  S_UPPER('S',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.S)
  ),
  SEMICOLON(';', KeyAction.type(Key.SEMICOLON)),
  SHARP('#', KeyCombo.press(Key.SHIFT, Key._3)),
  SLASH('/', KeyAction.type(Key.SLASH)),
  SPACE(' ', KeyAction.type(Key.SPACE)),
  SUPER_1('¹', KeyCombo.press(Key.ALTGR, Key._1)),
  SUPER_2('²', KeyCombo.press(Key.ALTGR, Key._2)),
  SUPER_3('³', KeyCombo.press(Key.ALTGR, Key._3)),
  SYMBOL_LEFT('←', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_2, Key.NUMPAD_7)) : KeyCombo.press(Key.ALTGR, Key.Y)),
  SYMBOL_UP('↑', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_2, Key.NUMPAD_4)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key.U)),
  SYMBOL_RIGHT('→', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_2, Key.NUMPAD_6)) : KeyCombo.press(Key.ALTGR, Key.I)),
  SYMBOL_DOWN('↓', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_2, Key.NUMPAD_5)) : KeyCombo.press(Key.ALTGR, Key.I)),

  T_LOWER('t',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.T)
  ),
  T_UPPER('T',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.T)
  ),
  TAB('\t', KeyAction.type(Key.TAB)),
  TILDE('~', KeyAction.type(Key.TILDE), KeyAction.type(Key.SPACE)),
  TM('™', OS.isWindows() ? ScriptCombo.of(r->r.setKeyLockState(Key.NUM_LOCK, true), KeyCombo.press(Key.ALT, Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_5, Key.NUMPAD_3)) : KeyCombo.press(Key.ALTGR, Key.SHIFT, Key._8)),

  U_LOWER('u',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.U)
  ),
  U_UPPER('U',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.U)
  ),
  U_GRAVE_LOWER('ù',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.U)
  ),
  U_GRAVE_UPPER('Ù',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.U)
  ),
  U_ACUTE_LOWER('ú', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.ACUTE, Key.U)
  ),
  U_ACUTE_UPPER('Ú', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.ACUTE, Key.U)
  ),
  U_CIRC_LOWER('û', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.U)
  ),
  U_CIRC_UPPER('Û', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.U)
  ),
  U_TILDE_LOWER('ũ', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.TILDE, Key.U)
  ),
  U_TILDE_UPPER('Ũ', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.TILDE, Key.U)
  ),
  U_UMBRA_LOWER('ü', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.U)
  ),
  U_UMBRA_UPPER('Ü', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.U)
  ),
  UNDERSCORE('_', KeyCombo.press(Key.SHIFT, Key.MINUS)),

  V_LOWER('v',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.V)
  ),
  V_UPPER('V',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.V)
  ),

  W_LOWER('w',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.W)
  ),
  W_UPPER('W',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.W)
  ),

  X_LOWER('x',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.X)
  ),
  X_UPPER('X',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.X)
  ),

  Y_LOWER('y',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.Y)
  ),
  Y_UPPER('Y',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyAction.type(Key.Y)
  ),
  Y_GRAVE_LOWER('ỳ',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.Y)
  ),
  Y_GRAVE_UPPER('Ỳ',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.Y)
  ),
  Y_ACUTE_LOWER('ý', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.ACUTE, Key.Y)
  ),
  Y_ACUTE_UPPER('Ý', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.ACUTE, Key.Y)
  ),
  Y_CIRC_LOWER('ŷ', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.Y)
  ),
  Y_CIRC_UPPER('Ŷ', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.Y)
  ),
  Y_TILDE_LOWER('ỹ', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.type(Key.TILDE, Key.Y)
  ),
  Y_TILDE_UPPER('Ỹ', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.type(Key.TILDE, Key.Y)
  ),
  Y_UMBRA_LOWER('ÿ', 
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.Y)
  ),
  Y_UMBRA_UPPER('Ÿ', 
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.Y)
  ),

  Z_LOWER('z',
      r->r.setKeyLockState(Key.CAPSLOCK, false), 
      KeyAction.type(Key.Z)
  ),
  Z_UPPER('Z',
      r->r.setKeyLockState(Key.CAPSLOCK, true), 
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
  NUM_LOCK_ENABLED((char)-16, r->r.setKeyLockState(Key.NUM_LOCK, true)),
  NUM_LOCK_DISABLED((char)-17, r->r.setKeyLockState(Key.NUM_LOCK, false)),
  SCROLL_LOCK((char)-18, KeyAction.type(Key.SCROLL_LOCK)),
  SCROLL_LOCK_ENABLED((char)-19, r->r.setKeyLockState(Key.SCROLL_LOCK, true)),
  SCROLL_LOCK_DISABLED((char)-20, r->r.setKeyLockState(Key.SCROLL_LOCK, false)),
  CAPS_LOCK((char)-21, KeyAction.type(Key.CAPSLOCK)),
  CAPS_LOCK_ENABLED((char)-22, r->r.setKeyLockState(Key.CAPSLOCK, true)),
  CAPS_LOCK_DISABLED((char)-23, r->r.setKeyLockState(Key.CAPSLOCK, false)),
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
