package org.springframework.samples.petclinic.validation;

import org.springframework.samples.petclinic.Owner;
import org.springframework.samples.petclinic.Pet;
import org.springframework.stereotype.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validate;

@Validator(validates=Pet.class)
public class PetValidator {
    @Validate
    public void checkDuplicates(Pet pet, Errors errors) {
        if (pet.isNew() && ownerHasExistingPetWithName(pet.getOwner(), pet.getName())) {
            errors.rejectValue("name", "duplicate", "already exists");
        }
    }

    private boolean ownerHasExistingPetWithName(Owner owner, String name) {
        return owner.getPet(name, true) != null;
    }
}
