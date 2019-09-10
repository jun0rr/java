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
  
  _0('0', KeyAction.type(Key._0)),
  _1('1', KeyAction.type(Key._1)),
  _2('2', KeyAction.type(Key._2)),
  _3('3', KeyAction.type(Key._3)),
  _4('4', KeyAction.type(Key._4)),
  _5('5', KeyAction.type(Key._5)),
  _6('6', KeyAction.type(Key._6)),
  _7('7', KeyAction.type(Key._7)),
  _8('8', KeyAction.type(Key._8)),
  _9('9', KeyAction.type(Key._9)),
  
  A_LOWER('a', 
      KeyAction.type(Key.A)
  ),
  A_UPPER('A', 
      KeyCombo.press(Key.SHIFT, Key.A)
  ),
  A_ACUTE_LOWER('á', 
      KeyCombo.type(Key.ACUTE, Key.A)
  ),
  A_ACUTE_UPPER('Á', 
      KeyAction.type(Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.A)
  ),
  A_CIRC_LOWER('â', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.A)
  ),
  A_CIRC_UPPER('Â', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.A)
  ),
  A_GRAVE_LOWER('à', 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.A)
  ),
  A_GRAVE_UPPER('À', 
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.A)
  ),
  A_TILDE_LOWER('ã', 
      KeyCombo.type(Key.TILDE, Key.A)
  ),
  A_TILDE_UPPER('Ã', 
      KeyAction.type(Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.A)
  ),
  A_UMBRA_LOWER('ä', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.A)
  ),
  A_UMBRA_UPPER('Ä', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyCombo.press(Key.SHIFT, Key.A)
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
      KeyAction.type(Key.B)
  ),
  B_UPPER('B', 
      KeyCombo.press(Key.SHIFT, Key.B)
  ),
  BACKSLASH('\\', KeyAction.type(Key.BACKSLASH)),
  BACKSPACE('\b', KeyAction.type(Key.BACKSPACE)),
  BRACELEFT('[', KeyAction.type(Key.BRACELEFT)),
  BRACERIGHT(']', KeyAction.type(Key.BRACERIGHT)),
  
  C_LOWER('c', 
      KeyAction.type(Key.C)
  ),
  C_UPPER('C', 
      KeyCombo.press(Key.SHIFT, Key.C)
  ),
  CCEDIL_LOWER('ç', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_2, Key.NUMPAD_3, Key.NUMPAD_1) 
      : ComposeKeyCombo.of(Key.COMMA, Key.C)
  ),
  CCEDIL_UPPER('Ç', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_1, Key.NUMPAD_2, Key.NUMPAD_8) 
      : ComposeKeyCombo.of(KeyAction.type(Key.COMMA), PressedKeyCombo.of(Key.SHIFT, Key.C))
  ),
  CIRC('^', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.SPACE)
  ),
  COLON(':', KeyCombo.press(Key.SHIFT, Key.SEMICOLON)),
  COMMA(',', KeyAction.type(Key.COMMA)),
  COPYRIGHT('©', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_6, Key.NUMPAD_9) 
      : ComposeKeyCombo.of(Key.O, Key.C)
  ),
  CURLY_BRACE_LEFT('{', KeyCombo.press(Key.SHIFT, Key.BRACELEFT)),
  CURLY_BRACE_RIGHT('}', KeyCombo.press(Key.SHIFT, Key.BRACERIGHT)),

  D_LOWER('d',
      KeyAction.type(Key.D)
  ),
  D_UPPER('D',
      KeyCombo.press(Key.SHIFT, Key.D)
  ),
  DEGREES('°', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_7, Key.NUMPAD_6) 
      : ComposeKeyCombo.of(Key.O, Key.O)
  ),
  DIVISION('÷', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_2, Key.NUMPAD_4, Key.NUMPAD_7) 
      : ComposeKeyCombo.of(PressedKeyCombo.of(Key.SHIFT, Key.SEMICOLON), KeyAction.type(Key.MINUS))
  ),
  DOLLAR('$', KeyCombo.press(Key.SHIFT, Key._4)),

  E_LOWER('e',
      KeyAction.type(Key.E)
  ),
  E_UPPER('E',
      KeyCombo.press(Key.SHIFT, Key.E)
  ),
  E_GRAVE_LOWER('è',
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.E)
  ),
  E_GRAVE_UPPER('È',
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.E)
  ),
  E_ACUTE_LOWER('é', 
      KeyCombo.type(Key.ACUTE, Key.E)
  ),
  E_ACUTE_UPPER('É', 
      KeyAction.type(Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.E)
  ),
  E_CIRC_LOWER('ê', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.E)
  ),
  E_CIRC_UPPER('Ê', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.E)
  ),
  E_TILDE_LOWER('ẽ', 
      KeyCombo.press(Key.TILDE, Key.E)
  ),
  E_TILDE_UPPER('Ẽ', 
      KeyAction.type(Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.E)
  ),
  E_UMBRA_LOWER('ë', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.E)
  ),
  E_UMBRA_UPPER('Ë', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyCombo.press(Key.SHIFT, Key.E)
  ),
  ENTER('\n', KeyAction.type(Key.ENTER)),
  EQUALS('=', KeyAction.type(Key.EQUALS)),
  EUR('€', OS.isWindows()
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_2, Key.NUMPAD_8) 
      : ComposeKeyCombo.of(Key.EQUALS, Key.C)
  ),
  EXCLAMATION('!', KeyCombo.press(Key.SHIFT, Key._1)),

  F_LOWER('f',
      KeyAction.type(Key.F)
  ),
  F_UPPER('F',
      KeyCombo.press(Key.SHIFT, Key.F)
  ),
  FRACTION_1_2('½', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_8, Key.NUMPAD_9) 
      : ComposeKeyCombo.of(Key._1, Key._2)
  ),
  FRACTION_3_4('¾', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_9, Key.NUMPAD_0) 
      : ComposeKeyCombo.of(Key._3, Key._4)
  ),
  FRACTION_1_4('¼', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_8, Key.NUMPAD_8) 
      : ComposeKeyCombo.of(Key._1, Key._4)
  ),
  
  G_LOWER('g',
      KeyAction.type(Key.G)
  ),
  G_UPPER('G',
      KeyCombo.press(Key.SHIFT, Key.G)
  ),
  GRAVE('`', KeyCombo.press(Key.SHIFT, Key.ACUTE), KeyAction.type(Key.SPACE)),
  GREATER('>', KeyCombo.press(Key.SHIFT, Key.PERIOD)),
  
  H_LOWER('h',
      KeyAction.type(Key.H)
  ),
  H_UPPER('H',
      KeyCombo.press(Key.SHIFT, Key.H)
  ),
  
  I_LOWER('i',
      KeyAction.type(Key.I)
  ),
  I_UPPER('I',
      KeyCombo.press(Key.SHIFT, Key.I)
  ),
  I_GRAVE_LOWER('ì',
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.I)
  ),
  I_GRAVE_UPPER('Ì',
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.I)
  ),
  I_ACUTE_LOWER('í', 
      KeyCombo.type(Key.ACUTE, Key.I)
  ),
  I_ACUTE_UPPER('Í', 
      KeyCombo.type(Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.I)
  ),
  I_CIRC_LOWER('î', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.I)
  ),
  I_CIRC_UPPER('Î', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.I)
  ),
  I_TILDE_LOWER('ĩ', 
      KeyCombo.type(Key.TILDE, Key.I)
  ),
  I_TILDE_UPPER('Ĩ', 
      KeyCombo.type(Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.I)
  ),
  I_UMBRA_LOWER('ï', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.I)
  ),
  I_UMBRA_UPPER('Ï', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyCombo.press(Key.SHIFT, Key.I)
  ),

  J_LOWER('j',
      KeyAction.type(Key.J)
  ),
  J_UPPER('J',
      KeyCombo.press(Key.SHIFT, Key.J)
  ),
  
  K_LOWER('k',
      KeyAction.type(Key.K)
  ),
  K_UPPER('K',
      KeyCombo.press(Key.SHIFT, Key.K)
  ),
  
  L_LOWER('l',
      KeyAction.type(Key.L)
  ),
  L_UPPER('L',
      KeyCombo.press(Key.SHIFT, Key.L)
  ),
  LEFTDBL('«', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_7, Key.NUMPAD_1) 
      : ComposeKeyCombo.of(PressedKeyCombo.of(Key.SHIFT, Key.COMMA, Key.COMMA))
  ),
  LESSER('<', KeyCombo.press(Key.SHIFT, Key.COMMA)),
  
  M_LOWER('m',
      KeyAction.type(Key.M)
  ),
  M_UPPER('M',
      KeyCombo.press(Key.SHIFT, Key.M)
  ),
  MINUS('-', KeyAction.type(Key.MINUS)),
  MORE_OR_LESS('±', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_7, Key.NUMPAD_7) 
      : ComposeKeyCombo.of(PressedKeyCombo.of(Key.SHIFT, Key.EQUALS), KeyAction.type(Key.MINUS))
  ),
  MULTIPLY('×', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_2, Key.NUMPAD_1, Key.NUMPAD_5) 
      : ComposeKeyCombo.of(Key.X, Key.X)
  ),
  
  N_LOWER('n',
      KeyAction.type(Key.N)
  ),
  N_UPPER('N',
      KeyCombo.press(Key.SHIFT, Key.N)
  ),
  NUMPAD_0('0', KeyAction.type(Key.NUMPAD_0)),
  NUMPAD_1('1', KeyAction.type(Key.NUMPAD_1)),
  NUMPAD_2('2', KeyAction.type(Key.NUMPAD_2)),
  NUMPAD_3('3', KeyAction.type(Key.NUMPAD_3)),
  NUMPAD_4('4', KeyAction.type(Key.NUMPAD_4)),
  NUMPAD_5('5', KeyAction.type(Key.NUMPAD_5)),
  NUMPAD_6('6', KeyAction.type(Key.NUMPAD_6)),
  NUMPAD_7('7', KeyAction.type(Key.NUMPAD_7)),
  NUMPAD_8('8', KeyAction.type(Key.NUMPAD_8)),
  NUMPAD_9('9', KeyAction.type(Key.NUMPAD_9)),
  
  O_LOWER('o',
      KeyAction.type(Key.O)
  ),
  O_UPPER('O',
      KeyCombo.press(Key.SHIFT, Key.O)
  ),
  O_GRAVE_LOWER('ò',
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.O)
  ),
  O_GRAVE_UPPER('Ò',
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.O)
  ),
  O_ACUTE_LOWER('ó', 
      KeyCombo.type(Key.ACUTE, Key.O)
  ),
  O_ACUTE_UPPER('Ó', 
      KeyCombo.type(Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.O)
  ),
  O_CIRC_LOWER('ô', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.O)
  ),
  O_CIRC_UPPER('Ô', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
     KeyCombo.press(Key.SHIFT, Key.O)
  ),
  O_TILDE_LOWER('õ', 
      KeyCombo.press(Key.TILDE, Key.O)
  ),
  O_TILDE_UPPER('Ô', 
      KeyCombo.type(Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.O)
  ),
  O_UMBRA_LOWER('ö', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.O)
  ),
  O_UMBRA_UPPER('Ö', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyCombo.press(Key.SHIFT, Key.O)
  ),
  
  P_LOWER('p',
      KeyAction.type(Key.P)
  ),
  P_UPPER('P',
      KeyCombo.press(Key.SHIFT, Key.P)
  ),
  PARAGRAPH('—', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_1, Key.NUMPAD_9, Key.NUMPAD_6) 
      : ComposeKeyCombo.of(Key.MINUS, Key.MINUS, Key.MINUS)
  ),
  PARENTHESIS_LEFT('(', KeyCombo.press(Key.SHIFT, Key._9)),  
  PARENTHESIS_RIGHT(')', KeyCombo.press(Key.SHIFT, Key._0)),  
  PERCENT('%', KeyCombo.press(Key.SHIFT, Key._5)),
  PERIOD('.', KeyAction.type(Key.PERIOD)),
  PIPE('|', KeyCombo.press(Key.SHIFT, Key.BACKSLASH)),
  PLUS('+', KeyCombo.press(Key.SHIFT, Key.EQUALS)),
  POUND('£', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_6, Key.NUMPAD_3) 
      : ComposeKeyCombo.of(Key.L, Key.MINUS)
  ),
  
  Q_LOWER('q',
      KeyAction.type(Key.Q)
  ),
  Q_UPPER('Q',
      KeyCombo.press(Key.SHIFT, Key.Q)
  ),
  QUOTE('\'', KeyAction.type(Key.QUOTE)),
  QUOTEDBL('"', KeyCombo.press(Key.SHIFT, Key.QUOTE)),
  QUESTION('?', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_6, Key.NUMPAD_3) 
      : PressedKeyCombo.of(Key.SHIFT, Key.SLASH)
  ),
  
  R_LOWER('r',
      KeyAction.type(Key.R)
  ),
  R_UPPER('R',
      KeyCombo.press(Key.SHIFT, Key.R)
  ),
  REGISTERED('®', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_7, Key.NUMPAD_4) 
      : ComposeKeyCombo.of(Key.O, Key.R)
  ),
  RIGHTDBL('»', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_8, Key.NUMPAD_7) 
      : ComposeKeyCombo.of(PressedKeyCombo.of(Key.SHIFT, Key.PERIOD, Key.PERIOD))
  ),

  S_LOWER('s',
      KeyAction.type(Key.S)
  ),
  S_UPPER('S',
      KeyCombo.press(Key.SHIFT, Key.S)
  ),
  SEMICOLON(';', KeyAction.type(Key.SEMICOLON)),
  SHARP('#', KeyCombo.press(Key.SHIFT, Key._3)),
  SLASH('/', KeyAction.type(Key.SLASH)),
  SPACE(' ', KeyAction.type(Key.SPACE)),
  SUPER_1('¹', KeyCombo.press(Key.ALTGR, Key._1)),
  SUPER_2('²', KeyCombo.press(Key.ALTGR, Key._2)),
  SUPER_3('³', KeyCombo.press(Key.ALTGR, Key._3)),
  SYMBOL_LEFT('←', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_2, Key.NUMPAD_7) 
      : ComposeKeyCombo.of(PressedKeyCombo.of(Key.SHIFT, Key.COMMA), KeyAction.type(Key.MINUS))
  ),
  SYMBOL_UP('↑', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_2, Key.NUMPAD_4) 
      : ScriptCombo.of(r->r.setClipboardString("↑"), PressedKeyCombo.of(Key.CTRL, Key.V))
  ),
  SYMBOL_RIGHT('→', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_2, Key.NUMPAD_6) 
      : ComposeKeyCombo.of(KeyAction.type(Key.MINUS), PressedKeyCombo.of(Key.SHIFT, Key.PERIOD))
  ),
  SYMBOL_DOWN('↓', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_2, Key.NUMPAD_5) 
      : ScriptCombo.of(r->r.setClipboardString("↓"), PressedKeyCombo.of(Key.CTRL, Key.V))
  ),

  T_LOWER('t',
      KeyAction.type(Key.T)
  ),
  T_UPPER('T',
      KeyCombo.press(Key.SHIFT, Key.T)
  ),
  TAB('\t', KeyAction.type(Key.TAB)),
  TILDE('~', KeyAction.type(Key.TILDE), KeyAction.type(Key.SPACE)),
  TM('™', OS.isWindows() 
      ? WindowsAltCombo.of(Key.NUMPAD_0, Key.NUMPAD_1, Key.NUMPAD_5, Key.NUMPAD_3) 
      : ComposeKeyCombo.of(Key.T, Key.M)
  ),

  U_LOWER('u',
      KeyAction.type(Key.U)
  ),
  U_UPPER('U',
      KeyCombo.press(Key.SHIFT, Key.U)
  ),
  U_GRAVE_LOWER('ù',
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.U)
  ),
  U_GRAVE_UPPER('Ù',
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.U)
  ),
  U_ACUTE_LOWER('ú', 
      KeyCombo.type(Key.ACUTE, Key.U)
  ),
  U_ACUTE_UPPER('Ú', 
      KeyCombo.type(Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.U)
  ),
  U_CIRC_LOWER('û', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.U)
  ),
  U_CIRC_UPPER('Û', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.U)
  ),
  U_TILDE_LOWER('ũ', 
      KeyCombo.press(Key.TILDE, Key.U)
  ),
  U_TILDE_UPPER('Ũ', 
      KeyCombo.type(Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.U)
  ),
  U_UMBRA_LOWER('ü', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.U)
  ),
  U_UMBRA_UPPER('Ü', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyCombo.press(Key.SHIFT, Key.U)
  ),
  UNDERSCORE('_', KeyCombo.press(Key.SHIFT, Key.MINUS)),

  V_LOWER('v',
      KeyAction.type(Key.V)
  ),
  V_UPPER('V',
      KeyCombo.press(Key.SHIFT, Key.V)
  ),

  W_LOWER('w',
      KeyAction.type(Key.W)
  ),
  W_UPPER('W',
      KeyCombo.press(Key.SHIFT, Key.W)
  ),

  X_LOWER('x',
      KeyAction.type(Key.X)
  ),
  X_UPPER('X',
      KeyCombo.press(Key.SHIFT, Key.X)
  ),

  Y_LOWER('y',
      KeyAction.type(Key.Y)
  ),
  Y_UPPER('Y',
      KeyCombo.press(Key.SHIFT, Key.Y)
  ),
  Y_GRAVE_LOWER('ỳ',
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyAction.type(Key.Y)
  ),
  Y_GRAVE_UPPER('Ỳ',
      KeyCombo.press(Key.SHIFT, Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.Y)
  ),
  Y_ACUTE_LOWER('ý', 
      KeyCombo.type(Key.ACUTE, Key.Y)
  ),
  Y_ACUTE_UPPER('Ý', 
      KeyCombo.type(Key.ACUTE),
      KeyCombo.press(Key.SHIFT, Key.Y)
  ),
  Y_CIRC_LOWER('ŷ', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyAction.type(Key.Y)
  ),
  Y_CIRC_UPPER('Ŷ', 
      KeyCombo.press(Key.SHIFT, Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.Y)
  ),
  Y_TILDE_LOWER('ỹ', 
      KeyCombo.press(Key.TILDE, Key.Y)
  ),
  Y_TILDE_UPPER('Ỹ', 
      KeyCombo.type(Key.TILDE),
      KeyCombo.press(Key.SHIFT, Key.Y)
  ),
  Y_UMBRA_LOWER('ÿ', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyAction.type(Key.Y)
  ),
  Y_UMBRA_UPPER('Ÿ', 
      KeyCombo.press(Key.SHIFT, Key._6),
      KeyCombo.press(Key.SHIFT, Key.Y)
  ),

  Z_LOWER('z',
      KeyAction.type(Key.Z)
  ),
  Z_UPPER('Z',
      KeyCombo.press(Key.SHIFT, Key.Z)
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
  public void accept(Robotnic r) {
    this.script.accept(r);
  }
  
  
  public static Optional<Keyboard> getByChar(char c) {
    return Stream.of(Keyboard.values()).filter(k -> k.getChar() == c).findAny();
  }
  
}
