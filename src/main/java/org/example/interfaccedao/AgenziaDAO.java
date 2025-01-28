package org.example.interfaccedao;

import org.example.dto.Agenzia;

public interface AgenziaDAO{

    public Agenzia getAgenziaByNome(String agenzia);

    public void saveAgenzia(Agenzia agenzia);

    public void updateAgenzia(Agenzia agenzia);

    public void deleteAgenziaByNome(String agenzia);

}
