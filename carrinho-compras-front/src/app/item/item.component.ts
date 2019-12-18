import { Component, OnInit } from '@angular/core';
import { Item } from '../shared/item';
import { SharedService } from '../shared/shared.service';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class ItemComponent implements OnInit {

  listItens: Item[];
  item: Item = new Item();
  mensagem: string;
  ativado: boolean = false;
  registerForm: FormGroup;
  editar: boolean = false;

  constructor(public service: SharedService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.montaForm();
    this.carregaLista();
  }

  montaForm(){
    this.registerForm = this.formBuilder.group({
      nome: ['', Validators.required],
      valor: ['', [Validators.required, Validators.min(0.1)]],
    });
  }

  submitForm() {
    this.item.nome = this.registerForm.value.nome;
    this.item.valor = this.registerForm.value.valor;
    if (this.registerForm.invalid) {
      this.ativado = true;
      return;
    }
    event.preventDefault();
    if(this.editar === true){
      this.service.UpdateItem(this.item).subscribe(() => {
        this.ativado = false;
        this.item = new Item();
        this.montaForm();
        this.carregaLista();
      });
    } else {
      this.service.CreateItem(this.item).subscribe(() => {
        this.ativado = false;
        this.item = new Item();
        this.montaForm();
        this.carregaLista();
      });
    }
  }

  carregaLista() {
    this.service.GetAllItens().subscribe(res => {
      this.listItens = res;
    });
  }

  removeItem(id: number){
    this.service.RemoveItem(id).subscribe(() => this.carregaLista());
  }

  editItem(item: Item){
    this.editar = true;
    this.item = item;
    this.registerForm = this.formBuilder.group({
      id: [item.id, Validators.required],
      nome: [item.nome, Validators.required],
      valor: [item.valor, [Validators.required, Validators.min(0.1)]],
    });
  }

}
