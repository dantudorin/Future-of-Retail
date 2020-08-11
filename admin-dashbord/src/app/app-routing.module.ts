import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CollectionListComponent } from './collection-list/collection-list.component';
import { CreateCollectionComponent } from './create-collection/create-collection.component';

const routes: Routes = [
  {
    path : 'allCollections', 
    component : CollectionListComponent
  },
  {
    path : 'create-collection',
    component : CreateCollectionComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [CollectionListComponent, CreateCollectionComponent];