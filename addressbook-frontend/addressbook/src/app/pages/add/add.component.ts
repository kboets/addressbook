import {Component, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {PersonService} from '../../services/person.service';
import {CountryService} from '../../services/country.service';
import {Person} from '../../domain/person';
import {Country} from '../../domain/country';
import {Router, RouterModule} from '@angular/router';

@Component({
  selector: 'app-add',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './add.component.html',
  styleUrl: './add.component.scss'
})
export class AddComponent implements OnInit {
  addPersonForm!: FormGroup;
  isSubmitting = false;
  public belgiumCountry: Country | undefined;
  public selectedCountry: Country | undefined;


  private fb = inject(FormBuilder);
  private personService = inject(PersonService);
  private countryService = inject(CountryService);
  private router = inject(Router);

  countries = this.countryService.allCountries;

  constructor() {
    this.initForm();
    this.belgiumCountry = this.countries().find(country => country.countryCode === 'BE');
    console.log('phonecode', this.belgiumCountry?.phoneCode);
    if (this.belgiumCountry?.phoneCode) {
      this.addPersonForm.patchValue({
        mobilePhoneCode: this.belgiumCountry.phoneCode,
      });
    }
  }
  ngOnInit(): void {
    this.initForm();
  }

  private initForm(): void {
    this.addPersonForm = this.fb.group({
      firstName: ['', [Validators.required]],
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      //mobilePhone: ['', [Validators.required]],
      // split into two controls
      mobilePhoneCode: ['', [Validators.required, Validators.pattern(/^\+\d{1,4}$/)]],
      mobilePhoneNumber: ['', [Validators.required, Validators.pattern(/^[0-9\s\-().]{6,}$/)]],

      mainAddress: this.fb.group({
        street: ['', [Validators.required]],
        houseNumber: ['', [Validators.required]],
        box: [''],
        zipCode: ['', [Validators.required]],
        city: ['', [Validators.required]],
        countryDto: this.fb.group({
          id: [null],
          name: ['', [Validators.required]],
          countryCode: ['', [Validators.required]]
        })
      })
    });
  }

  onSubmit(): void {
    if (this.addPersonForm.invalid) {
      // Mark all fields as touched to trigger validation messages
      this.markFormGroupTouched(this.addPersonForm);
      return;
    }

    this.isSubmitting = true;
    const personData: Person = this.addPersonForm.value;

    this.personService.registerPerson(personData).subscribe({
      next: () => {
        this.isSubmitting = false;
        // Navigate to overview page after successful submission
        this.router.navigate(['/overview']);
      },
      error: (error) => {
        this.isSubmitting = false;
        //console.error('Error saving person:', error);
        // Handle error (could add error message display here)
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/overview']);
  }

  // Helper method to mark all controls in a form group as touched
  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();

      if ((control as FormGroup).controls) {
        this.markFormGroupTouched(control as FormGroup);
      }
    });
  }

  // Helper method to check if a field is invalid and touched
  isFieldInvalid(controlName: string): boolean {
    const control = this.addPersonForm.get(controlName);
    return control ? (control.invalid && (control.dirty || control.touched)) : false;
  }

  // Helper method to check if a nested field is invalid and touched
  isNestedFieldInvalid(path: string[]): boolean {
    const control = this.addPersonForm.get(path);
    return control ? (control.invalid && (control.dirty || control.touched)) : false;
  }

  // Method to handle country selection
  onCountrySelect(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const countryId = selectElement.value;

    if (countryId) {
      const selectedCountry = this.countries().find(country => country.id === Number(countryId));

      if (selectedCountry) {
        const countryGroup = this.addPersonForm.get('mainAddress.countryDto');
        if (countryGroup) {
          countryGroup.patchValue({
            id: selectedCountry.id,
            name: selectedCountry.name,
            countryCode: selectedCountry.countryCode
          });
        }
      }
    }
  }
}
