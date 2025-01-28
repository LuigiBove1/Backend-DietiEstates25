package org.example.interfaccedao;

import org.example.dto.Agenzia;

public interface AgenziaDAO{

    public Agenzia getAgenziaByNome(String agenzia);

    public boolean saveAgenzia(Agenzia agenzia);

    public boolean updateAgenzia(Agenzia agenzia);

    public boolean deleteAgenziaByNome(String agenzia);

}
