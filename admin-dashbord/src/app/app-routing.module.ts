import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CollectionListComponent } from './collection-list/collection-list.component';
import { CreateCollectionComponent } from './create-collection/create-collection.component';
import { NotFoundComponent } from './not-found/not-found.component';
const routes: Routes = [
  { 
    path : '',
    redirectTo : 'allCollections',
    pathMatch : 'full'
  },
  { 
    path : 'allCollections', 
    component : CollectionListComponent
  },
  {
    path : 'create-collection',
    component : CreateCollectionComponent
  },
  {
    path : '**',
    component : NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [CollectionListComponent, CreateCollectionComponent];
