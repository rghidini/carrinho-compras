import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { SharedService } from '../shared/shared.service';
import { Carrinho } from '../shared/carrinho';

@Component({
  selector: 'app-lista-compra',
  templateUrl: './lista-compra.component.html',
  styleUrls: ['./lista-compra.component.scss']
})
export class ListaCompraComponent implements OnInit {
  @Input() userId: string;
  registerForm: FormGroup;
  listCarrinhos: Array<Carrinho> = [];
  ativado: boolean = false;

  constructor(
    public service: SharedService, 
    private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.montaForm();
  }

  montaForm() {
    this.registerForm = this.formBuilder.group({
      idUsuario: ['', Validators.required]
    });
  }

  carregaLista() {
    if (this.registerForm.invalid) {
      this.ativado = true;
      return;
    }
    this.service.GetCarrinhoByUserId(this.registerForm.value.idUsuario).subscribe(res => {
      this.listCarrinhos = res;
    });
  }

}
