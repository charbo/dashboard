package java8;

import java8.bean.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LabelServiceTest {

  public static final String ID_COMMANDE = "idCommande";
  public static final String NUMBER = "number";
  private LabelService labelService;

  private ClientAdapter clientAdapter = Mockito.mock(ClientAdapter.class);

  @BeforeEach public void
  init() {
    labelService = new LabelService(clientAdapter);
  }

  @Test public void
  id_commande_null() {
    String label = labelService.getLabelClientNumber(null);
    assertEquals("Non communiqué", label);
  }

  @Test public void
  client_is_unknown() {
    when(clientAdapter.getClientReferenceByCommande(ID_COMMANDE)).thenReturn(null);
    String label = labelService.getLabelClientNumber(ID_COMMANDE);
    assertEquals("Non communiqué", label);
  }

  @Test public void
  client_s_number_is_unknown() {
    when(clientAdapter.getClientReferenceByCommande(ID_COMMANDE)).thenReturn(new Client());
    String label = labelService.getLabelClientNumber(ID_COMMANDE);
    assertEquals("Non communiqué", label);
  }

  @Test public void
  client_s_number_is_known() {
    when(clientAdapter.getClientReferenceByCommande(ID_COMMANDE)).thenReturn(new Client(NUMBER));
    String label = labelService.getLabelClientNumber(ID_COMMANDE);
    assertEquals(NUMBER, label);
  }


}