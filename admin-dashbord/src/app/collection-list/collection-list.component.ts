import { Component, OnInit } from '@angular/core';
import { CollectionService } from '../service/collection.service';
import { Collection } from '../model/collection';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-collection-list',
  templateUrl: './collection-list.component.html',
  styleUrls: ['./collection-list.component.css']
})
export class CollectionListComponent implements OnInit {
  loading = false;
  errorMessage = '';
  collections: Collection[];

  constructor(private collectionService: CollectionService) { }

  ngOnInit(): void {
    this.loading = true;
    this.collectionService.getAllCollections()
      .subscribe(collections => {
        this.errorMessage = '';
        this.collections = [...collections];
        this.loading = false;
      },
        error => {
          this.loading = false;
          this.errorMessage = error;
        });
  }
}
