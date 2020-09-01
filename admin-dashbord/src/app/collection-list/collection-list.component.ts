import { Component, OnInit } from '@angular/core';
import { CollectionService } from '../service/collection.service';
import { Collection } from '../model/collection';
import { Observable } from 'rxjs';
import { NumberItems } from '../model/numberItems';


@Component({
  selector: 'app-collection-list',
  templateUrl: './collection-list.component.html',
  styleUrls: ['./collection-list.component.css']
})
export class CollectionListComponent implements OnInit {
  loading = false;
  errorMessage = '';
  emptyList : boolean;
  collections: Collection[];
  searchByName: string = "";

  page: Number = 1
  items: Number = 1
  selectBy = "id"
  numberSelected: any = {};
  numberItems: NumberItems[];


  constructor(private collectionService: CollectionService) { }

  ngOnInit(): void {
    this.loading = true;
    this.emptyList = false;
    this.errorMessage = '';
    this.numberItems = [
      { number: 1 },
      { number: 2 },
      { number: 3 },
      { number: 4 },
      { number: 5 },
    ];

    this.collectionService.getCollectionFromPage(this.page, this.items, this.selectBy)
      .subscribe(collections => {
        this.errorMessage = '';
        
        if(collections.length > 0) {
          this.collections = [...collections];
          console.log(collections);
        } else {
          this.emptyList = true;
        }
      
        this.loading = false;
      },
        error => {
          this.loading = false;
          this.errorMessage = error;
        });
  }
  onNumberSelected(val: NumberItems) {
    this.items = val.number;
  }
}
