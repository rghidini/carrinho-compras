import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsuarioComponent } from './usuario/usuario.component';
import { ItemComponent } from './item/item.component';
import { CarrinhoComponent } from './carrinho/carrinho.component';
import { ListaCompraComponent } from './lista-compra/lista-compra.component';


const routes: Routes = [
  {path: '', component: UsuarioComponent},
  {path: 'usuario', component: UsuarioComponent},
  {path: 'item', component: ItemComponent},
  {path: 'carrinho', component: CarrinhoComponent},
  {path: 'lista', component: ListaCompraComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
