package diet.model;

import java.time.LocalDateTime;

public class Diet {
    private int idDiet;
    private int idOsoba;
    private LocalDateTime date;

    public Diet(int idDiet, int idOsoba, LocalDateTime date) {
        this.idDiet = idDiet;
        this.idOsoba = idOsoba;

        this.date = date;
    }

    public int getIdDiet() {
        return idDiet;
    }

    public void setIdDiet(int idDiet) {
        this.idDiet = idDiet;
    }

    public int getIdOsoba() {
        return idOsoba;
    }

    public void setIdOsoba(int idOsoba) {
        this.idOsoba = idOsoba;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return idDiet+" | "+idOsoba+" | "+date;
    }
}
