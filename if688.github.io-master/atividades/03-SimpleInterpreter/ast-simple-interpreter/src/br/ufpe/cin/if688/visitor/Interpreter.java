package br.ufpe.cin.if688.visitor;

import br.ufpe.cin.if688.ast.AssignStm;
import br.ufpe.cin.if688.ast.CompoundStm;
import br.ufpe.cin.if688.ast.EseqExp;
import br.ufpe.cin.if688.ast.Exp;
import br.ufpe.cin.if688.ast.ExpList;
import br.ufpe.cin.if688.ast.IdExp;
import br.ufpe.cin.if688.ast.LastExpList;
import br.ufpe.cin.if688.ast.NumExp;
import br.ufpe.cin.if688.ast.OpExp;
import br.ufpe.cin.if688.ast.PairExpList;
import br.ufpe.cin.if688.ast.PrintStm;
import br.ufpe.cin.if688.ast.Stm;
import br.ufpe.cin.if688.symboltable.Table;
import javafx.scene.control.Tab;

public class Interpreter implements IVisitor<Table> {
	

	// a->7 ==> b->80 ==> a->8 ==> NIL
	private Table t;
	private Table aux;
	public Interpreter(Table t) {
		this.t = t;
	}

	@Override
	public Table visit(Stm s) {
		// TODO Auto-generated method stub
		s.accept(this);
		return t;
	}

	@Override
	public Table visit(AssignStm s) {
		// TODO Auto-generated method stub

		Table t1 = new Table(s.getId(), s.getExp().accept(this).value, t);
		t = t1;

		return t;
	}

	@Override
	public Table visit(CompoundStm s) {
		Table t1 = s.getStm1().accept(this);
		Table t2 = s.getStm2().accept(this);

		return t;
	}

	@Override
	public Table visit(PrintStm s) {
		// TODO Auto-generated method stub
		Table t1 = s.getExps().accept(this);
		Table t2 = new Table(t1.id, t1.value, t1.tail);
		while(t2 != null){
			System.out.print(t2.value + "\n");
			t2 = t2.tail;
		}
		return null;
	}

	@Override
	public Table visit(Exp e) {
		// TODO Auto-generated method stub

		return t;
	}

	@Override
	public Table visit(EseqExp e) {
		e.getStm().accept(this);
		Table t1 = e.getExp().accept(this);
		return t1;
	}

	@Override
	public Table visit(IdExp e) {
		//TO-DO
		double value = t.value ;
		Table t1 = new Table(t.id, t.value,t.tail);
		while(t1.id !=  e.getId())
		{
			t1 = t1.tail;
		}
		value = t1.value;
		Table t2 = new Table(e.getId(), value, null);
		return t2;
	}

	@Override
	public Table visit(NumExp e) {
		Table t1 = new Table("", e.getNum(),null);
		return t1;
	}

	@Override
	public Table visit(OpExp e) {
		Table t1 = e.getLeft().accept(this);
		Table t2 = e.getRight().accept(this);
		Table t3 = null;
		if(e.getOper() == 1){
			t3 = new Table("", t1.value + t2.value, null);
		}
		if(e.getOper() == 3){
			t3 = new Table("", t1.value * t2.value, null);
		}
		if(e.getOper() == 2){
			t3 = new Table("", t1.value - t2.value, null);
		}
		if(e.getOper() == 4){
			t3 = new Table("", t1.value / t2.value, null);
		}
		return t3;
	}

	@Override
	public Table visit(ExpList el) {

		return t;
	}

	@Override
	public Table visit(PairExpList el) {
		Table t1 = new Table("", el.getHead().accept(this).value, el.getTail().accept(this));
		return t1;
	}

	@Override
	public Table visit(LastExpList el) {
		// TODO Auto-generated method stu
		Table t1 = el.getHead().accept(this);
		return t1;
	}


}
