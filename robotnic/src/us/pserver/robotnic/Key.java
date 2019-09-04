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
public enum Key {
  
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
  ACUTE(KeyEvent.VK_DEAD_ACUTE, '\''),
  ALT(KeyEvent.VK_ALT, "ALT"),
  ALTGR(KeyEvent.VK_ALT_GRAPH, "ALTGR"),
  AMPERSAND(KeyEvent.VK_AMPERSAND, '&'),
  ARROW_DOWN(KeyEvent.VK_DOWN, '↓'),
  ARROW_LEFT(KeyEvent.VK_LEFT, '←'),
  ARROW_RIGHT(KeyEvent.VK_RIGHT, '→'),
  ARROW_UP(KeyEvent.VK_UP, '↑'),
  ASTERISC(KeyEvent.VK_ASTERISK, '*'),
  AT(KeyEvent.VK_AT, '@'),
  
  B(KeyEvent.VK_B, 'b'),
  BACKSLASH(KeyEvent.VK_BACK_SLASH, '\\'),
  BACKSPACE(KeyEvent.VK_BACK_SPACE, "BACKSPACE"),
  BRACELEFT(KeyEvent.VK_OPEN_BRACKET, '['),
  BRACERIGHT(KeyEvent.VK_CLOSE_BRACKET, ']'),
  
  C(KeyEvent.VK_C, 'c'),
  CAPSLOCK(KeyEvent.VK_CAPS_LOCK, "CAPSLOCK"),
  COMMA(KeyEvent.VK_COMMA, ','),
  CTRL(KeyEvent.VK_CONTROL, "CTRL"),
  
  D(KeyEvent.VK_D, 'd'),
  DELETE(KeyEvent.VK_DELETE, "DEL"),
  DOLLAR(KeyEvent.VK_DOLLAR, '$'),
  
  E(KeyEvent.VK_E, 'e'),
  END(KeyEvent.VK_END, "END"),
  ENTER(KeyEvent.VK_ENTER, "ENTER"),
  EQUALS(KeyEvent.VK_EQUALS, '='),
  ESCAPE(KeyEvent.VK_ESCAPE, "ESC"),
  EXCLAMATION(KeyEvent.VK_EXCLAMATION_MARK, '!'),
  
  F(KeyEvent.VK_F, 'f'),
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
  GRAVE(KeyEvent.VK_DEAD_GRAVE, '`'),
  GREATER(KeyEvent.VK_GREATER, '>'),
  
  H(KeyEvent.VK_H, 'h'),
  HOME(KeyEvent.VK_HOME, "HOME"),
  
  I(KeyEvent.VK_I, 'i'),
  INSERT(KeyEvent.VK_INSERT, 'i'),
  
  J(KeyEvent.VK_J, 'j'),
  K(KeyEvent.VK_K, 'k'),
  L(KeyEvent.VK_L, 'l'),

  M(KeyEvent.VK_M, 'm'),
  MINUS(KeyEvent.VK_MINUS, '-'),
  
  N(KeyEvent.VK_N, 'n'),
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
  
  P(KeyEvent.VK_P, 'p'),
  PARENTESIS_LEFT(KeyEvent.VK_LEFT_PARENTHESIS, '('),
  PARENTESIS_RIGHT(KeyEvent.VK_RIGHT_PARENTHESIS, ')'),
  PG_DOWN(KeyEvent.VK_PAGE_DOWN, "PG_DOWN"),
  PG_UP(KeyEvent.VK_PAGE_UP, "PG_UP"),
  PERIOD(KeyEvent.VK_PERIOD, '.'),
  PLUS(KeyEvent.VK_PLUS, '+'),
  PRINTSCREEN(KeyEvent.VK_PRINTSCREEN, "PRINTSCREEN"),
  
  Q(KeyEvent.VK_Q, 'q'),
  QUOTE(KeyEvent.VK_QUOTE, '\''),
  QUOTEDBL(KeyEvent.VK_QUOTEDBL, '"'),
  
  R(KeyEvent.VK_R, 'r'),
  
  S(KeyEvent.VK_S, 's'),
  SCROLL_LOCK(KeyEvent.VK_SCROLL_LOCK, "SCROLL_LOCK"),
  SEMICOLON(KeyEvent.VK_SEMICOLON, ';'),
  SHIFT(KeyEvent.VK_SHIFT, "SHIFT"),
  SLASH(KeyEvent.VK_SLASH, '/'),
  SPACE(KeyEvent.VK_SPACE, ' '),
  
  T(KeyEvent.VK_T, 't'),
  TILDE(KeyEvent.VK_DEAD_TILDE, '~'),
  TAB(KeyEvent.VK_TAB, "TAB"),
  
  U(KeyEvent.VK_U, 'u'),
  UNDERSCORE(KeyEvent.VK_UNDERSCORE, '_'),
  
  V(KeyEvent.VK_V, 'v'),
  W(KeyEvent.VK_W, 'w'),
  WIN(KeyEvent.VK_WINDOWS, "WIN"),
  
  X(KeyEvent.VK_X, 'x'),
  Y(KeyEvent.VK_Y, 'y'),
  Z(KeyEvent.VK_Z, 'z'),
  ;
  
  private Key(int cd, char ch) {
    this.code = cd;
    this.ch = ch;
    this.str = Character.toString(ch);
  }
  
  private Key(int cd, String str) {
    this.code = cd;
    this.ch = 0;
    this.str = str;
  }
  
  private final int code;
  
  private final char ch;
  
  private final String str;
  
  public int getCode() {
    return code;
  }
  
  public char getChar() {
    return ch;
  }
  
  @Override
  public String toString() {
    return str;
  }
  
}
