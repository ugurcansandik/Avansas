function deleteUser(username){
     const deleteModal = document.getElementById('deleteButton')
         deleteModal.addEventListener('click', () => {
             $.ajax({
                 url: 'http://localhost:8080/delete/' + username,
                 method: 'DELETE',
                 success: function (response) {
                     window.location.reload();
                 }
             });
         })
}
