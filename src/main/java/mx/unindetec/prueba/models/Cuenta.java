package mx.unindetec.prueba.models;

import mx.unindetec.prueba.exceptions.SaldoInsuficienteException;

import java.math.BigDecimal;

public class Cuenta {
    private String persona;
    private BigDecimal saldo;

    public Cuenta(String persona, BigDecimal saldo) {
        this.saldo = saldo;
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public void debito(BigDecimal monto){
        BigDecimal temporal = saldo.subtract(monto);
        if(temporal.compareTo(BigDecimal.ZERO) < 0){
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        this.saldo = saldo.subtract(monto);
    }

    public void credito(BigDecimal monto){
        this.saldo = saldo.add(monto);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Cuenta)){
            return false;
        }

        Cuenta c = (Cuenta)obj;

        if(this.persona == null || this.saldo == null){
            return false;
        }

        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }
}
