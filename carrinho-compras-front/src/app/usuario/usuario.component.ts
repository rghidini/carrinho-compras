import { Component, OnInit } from '@angular/core';
import { SharedService } from '../shared/shared.service';
import { Usuario } from '../shared/usuario';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  listUsers: Usuario[];
  usuario: Usuario = new Usuario();
  mensagem: string;
  ativado: boolean = false;
  editar: boolean = false;
  registerForm: FormGroup;

  constructor(public service: SharedService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.montaForm();
    this.carregaLista();
  }

  montaForm(){
    this.registerForm = this.formBuilder.group({
      id: ['', Validators.required],
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
    });
  }

  submitForm() {
    this.usuario.id = this.registerForm.value.id;
    this.usuario.nome = this.registerForm.value.nome;
    this.usuario.email = this.registerForm.value.email;
    if (this.registerForm.invalid) {
      this.ativado = true;
      return;
    }
    event.preventDefault();
    if(this.editar === true){
      this.service.UpdateUser(this.usuario).subscribe(() => {
        this.ativado = false;
        this.usuario = new Usuario();
        this.montaForm();
        this.carregaLista();
      }, erro => {
        this.mensagem = "Já existe um usuário com esse ID";
        this.ativado = true;
      });
    } else {
      this.service.CreateUser(this.usuario).subscribe(() => {
        this.ativado = false;
        this.usuario = new Usuario();
        this.montaForm();
        this.carregaLista();
      }, erro => {
        this.mensagem = "Já existe um usuário com esse ID";
        this.ativado = true;
      });
    }
  }

  carregaLista() {
    this.service.GetAllUsers().subscribe(res => {
      this.listUsers = res;
    });
  }

  removeUser(id: string){
    this.service.RemoveUser(id).subscribe(() => this.carregaLista());
  }

  editUser(usuario: Usuario){
    this.editar = true;
    this.registerForm = this.formBuilder.group({
      id: [usuario.id, Validators.required],
      nome: [usuario.nome, Validators.required],
      email: [usuario.email, [Validators.required, Validators.email]],
    });
  }

}
