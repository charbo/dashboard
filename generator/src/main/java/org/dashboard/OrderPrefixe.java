package org.dashboard;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public enum OrderPrefixe {
  P_OD1("OD1"),
  P_33G("33G"),
  P_04O("04O"),
  P_22F("22F"),
  P_32R("32R"),
  P_M5A("M5A"),
  P_45R("45R"),
  P_42C("LP"),
  P_M22("M22"),
  P_74F("74F"),
  P_72T("72T"),
  P_WKF("WKF"),
  P_M7W("M7W"),
  P_INCONNU("---");

  public static final List<OrderPrefixe> ORDER_PREFIXES = Arrays.asList(OrderPrefixe.values());

  public final String prefixe;

  OrderPrefixe(final String prefixe) {
    this.prefixe = prefixe;
  }


}
