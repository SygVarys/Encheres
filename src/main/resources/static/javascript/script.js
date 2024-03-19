/**
 * l'utilisateur doit valider un bouton radio pour débloquer la checkbox située en dessous
 */

 
  // Récupérer les éléments HTML
  
  const radios = document.querySelectorAll('input[name="choixRecherche"]');
  let enchOuvertesCheckbox = document.getElementById('enchOuvertes');
  let enchEnCoursCheckbox = document.getElementById('enchEnCours');
  let enchRemporteesCheckbox = document.getElementById('enchRemportees');
  let ventesEnCoursCheckbox = document.getElementById('ventesEnCours');
  let venteNonDebuteeCheckbox = document.getElementById('venteNonDebutee');
  let ventesTermineesCheckbox = document.getElementById('ventesTerminees');

		

  // Ajouter des gestionnaires d'événements pour le changement d'état des boutons radio
  radios.forEach(function(radio) {
    radio.addEventListener('change', function() {
      // Activer ou désactiver les cases à cocher en fonction de l'état du bouton radio sélectionné
      enchOuvertesCheckbox.disabled = radio.value !== 'achat';
      enchEnCoursCheckbox.disabled = radio.value !== 'achat';
      enchRemporteesCheckbox.disabled = radio.value !== 'achat';
      ventesEnCoursCheckbox.disabled = radio.value !== 'mesVentes';
      venteNonDebuteeCheckbox.disabled = radio.value !== 'mesVentes';
      ventesTermineesCheckbox.disabled = radio.value !== 'mesVentes';
    });
  });
