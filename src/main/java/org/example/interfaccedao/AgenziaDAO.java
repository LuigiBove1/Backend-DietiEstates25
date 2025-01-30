package org.example.interfaccedao;

import org.example.dto.Agenzia;

public interface AgenziaDAO{

    Agenzia getAgenziaByNome(String agenzia);

    void saveAgenzia(Agenzia agenzia);

    void updateAgenzia(Agenzia agenzia);

    void deleteAgenziaByNome(String agenzia);

}
