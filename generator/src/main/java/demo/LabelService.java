package demo;


import demo.bean.Client;

import java.util.Optional;

public class LabelService {
  private ClientAdapter clientAdapter;

  public LabelService(ClientAdapter clientAdapter) {
    this.clientAdapter = clientAdapter;
  }

  public String getLabelClientNumber(String idCommande) {
    String result;
    if (idCommande != null) {
      Client client = clientAdapter.getClientReferecneByCommande(idCommande);
      if (client != null && client.getNumber() != null) {
        result = client.getNumber();
      } else {
        result = "Non communiqué";
      }
    } else {
      result = "Non communiqué";
    }
    return result;
  }

  public String getLabelRefactored(String idCommande) {
    return Optional.ofNullable(idCommande)
            .map(id -> clientAdapter.getClientReferecneByCommande(idCommande))
            .map(client -> client.getNumber()).orElseGet(() -> "Non communiqué");
  }
}
