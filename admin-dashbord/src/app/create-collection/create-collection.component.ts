import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { CollectionService } from '../service/collection.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-create-collection',
  templateUrl: './create-collection.component.html',
  styleUrls: ['./create-collection.component.css']
})
export class CreateCollectionComponent implements OnInit {

  collectionForm: FormGroup;
  errorMessage = '';
  loading = false;

  constructor(private collectionService: CollectionService, private formBuilder: FormBuilder, private router : Router) { }

  ngOnInit(): void {
    this.collectionForm = this.formBuilder.group({
      name: new FormControl('', [Validators.required, Validators.minLength(5)]),
      location: new FormControl('', [Validators.required, Validators.minLength(5)])
    });

    this.collectionForm.valueChanges.subscribe(console.log);
  }
  onSubmit() {
    this.errorMessage = '';
    this.loading = true;
    let newCollection = {
      name: this.collectionForm.value.name,
      location: this.collectionForm.value.location,
    }
    console.log(newCollection);

    this.collectionService.createCollection(newCollection).subscribe(
      collection => {
        this.loading = false;
        this.router.navigateByUrl('allCollections');
      },
      error => {
        this.loading = false;
        this.errorMessage = error;
      });
  }

  isDisabled() {
    if (this.collectionForm.get('name').hasError('required') ||
      this.collectionForm.get('location').hasError('required') ||
      this.collectionForm.get('name').hasError('minlength') ||
      this.collectionForm.get('location').hasError('minlength'))
      return true;
    else
      return false;
  }
}
