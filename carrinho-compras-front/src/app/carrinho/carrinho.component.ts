import { Component, OnInit } from '@angular/core';
import { Carrinho } from '../shared/carrinho';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { SharedService } from '../shared/shared.service';
import { Usuario } from '../shared/usuario';
import { Item } from '../shared/item';
import { ItemCarrinho } from '../shared/item carrinho';
import { Router } from '@angular/router';

@Component({
  selector: 'app-carrinho',
  templateUrl: './carrinho.component.html',
  styleUrls: ['./carrinho.component.scss']
})
export class CarrinhoComponent implements OnInit {

  listCarrinhos: Carrinho[];
  listUsers: Usuario[];
  listItens: Item[];
  listItensCarrinho: Array<ItemCarrinho> = [];
  item: Item = new Item();
  carrinho: Carrinho = new Carrinho();
  ativado: boolean = false;
  registerForm: FormGroup;

  constructor(
    public service: SharedService, 
    private formBuilder: FormBuilder,
    private router: Router) { }

  ngOnInit() {
    this.montaForm();
    this.carregaLista();
  }

  montaForm() {
    this.registerForm = this.formBuilder.group({
      idUsuario: new FormControl({value:'', disabled: false}, Validators.required),
      itensNome: ['', Validators.required],
      itensQuantidade: ['', [Validators.required, Validators.min(1)]]
    });
  }

  submitForm() { 
    this.service.FecharCompras(this.carrinho).subscribe(() => {
      this.ativado = false;
      this.carrinho = new Carrinho();
      this.listItensCarrinho = [];
      this.carregaLista();
      this.router.navigateByUrl('/lista');
    });
    
  }

  carregaLista() {
    this.service.GetAllUsers().subscribe(res => {
      this.listUsers = res;
    });
    this.service.GetAllItens().subscribe(res => {
      this.listItens = res;
    });
  }

  getItem(e) {
    this.item.valor = this.listItens.find(x => x.id == e.target.value).valor;
    this.item.nome = this.listItens.find(x => x.id == e.target.value).nome;
  }

  getUser(e) {
    this.carrinho.idUsuario = this.registerForm.value.idUsuario;
    this.registerForm.get('idUsuario').disable({onlySelf: true});
  }

  adicionar() {
    if (this.registerForm.invalid) {
      this.ativado = true;
      return;
    }
    let itemCarrinho: ItemCarrinho = new ItemCarrinho();
    itemCarrinho.nome = this.item.nome;
    itemCarrinho.quantidade = this.registerForm.value.itensQuantidade;
    itemCarrinho.valor = this.item.valor;
    if (this.listItensCarrinho.find(x => x.nome == itemCarrinho.nome) != undefined) {
      let quantidade = Number(this.listItensCarrinho.find(x => x.nome == itemCarrinho.nome).quantidade) + Number(itemCarrinho.quantidade);
      itemCarrinho.quantidade = quantidade;
      let item: ItemCarrinho = this.listItensCarrinho.find(x => x.nome == this.item.nome);
      this.listItensCarrinho.splice(this.listItensCarrinho.indexOf(item), 1);
    }
    this.listItensCarrinho.push(itemCarrinho);
    this.registerForm = this.formBuilder.group({
      itensNome: ['', Validators.required],
      itensQuantidade: ['', [Validators.required, Validators.min(1)]]
    });
    this.item = new Item();
    this.sumValorTotal();
    this.carrinho.itens = this.listItensCarrinho;
  }

  removeItem(item: ItemCarrinho) {
    this.listItensCarrinho.splice(this.listItensCarrinho.indexOf(item), 1);
  }

  sumValorTotal(){
    this.carrinho.valorTotal = this.listItensCarrinho.reduce((sum, val) => sum += val.valor*val.quantidade,0);
  }

}
