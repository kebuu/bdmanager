package com.cta.batch.model;

import lombok.Data;

@Data
public class BdFromCsv {

    protected String serie;
    protected Integer tome;
    protected String titre;
    protected String sousTitre;
    protected String auteurs;
    protected String editeur;
}
