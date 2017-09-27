package java8;


import java8.bean.Client;

import java.util.Optional;

public class LabelService {
  private ClientAdapter clientAdapter;

  public LabelService(ClientAdapter clientAdapter) {
    this.clientAdapter = clientAdapter;
  }

  public String getLabelClientNumber(String idCommande) {
    String result;
    if (idCommande != null) {
      Client client = clientAdapter.getClientReferenceByCommande(idCommande);
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

/*  public String getLabelRefactored(String idCommande) throws NullPointerException {
    return Optional.of(Optional.ofNullable(idCommande).orElseThrow(NullPointerException::new))
            .map(id -> clientAdapter.getClientReferenceByCommande(idCommande))
            .map(client -> client.getNumber()).orElseGet(() -> "Non communiqué");
  }*/

  public String getLabelRefactored(String idCommande) throws NullPointerException {
    String value = Optional.ofNullable(idCommande).orElseThrow(NullPointerException::new);
    return Optional.of(value)
            .map(id -> clientAdapter.getClientReferenceByCommande(idCommande))
            .map(client -> client.getNumber()).orElseGet(() -> "Non communiqué");
  }
}
