function modalConvidarFuncionario() {
    $('#modalConvidarFuncionario').modal('show');
}

function convidarFuncionario() {
    var emailFuncionario = document.getElementById('emailFuncionario').value;

    Swal.fire({
        title: "Enviando convite...",
        text: "Aguarde enquanto enviamos o email.",
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    $.ajax({
        url: '/empresa/convidarfuncionario',
        type: 'POST',
        data: {
            emailFuncionario: emailFuncionario
        },
        complete: function(xhr, status) {
            switch (xhr.status) {
                case 200:
                    Swal.fire({
                        title: "Pronto!",
                        text: "Funcionário convidado com sucesso. Peça para que o mesmo verifique sua caixa de entrada.",
                        icon: "success",
                        confirmButtonText: 'OK'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            location.reload();
                        }
                    });
                    break;
                case 404:
                    Swal.fire({
                        title: "Ops!",
                        text: "Não existe um usuário com esse email.",
                        icon: "warning",
                        confirmButtonText: "OK"
                    })
                    break;
                case 400:
                    Swal.fire({
                        title: "Erro!",
                        text: "Ocorreu um erro ao convidar este funcionário.",
                        icon: "error"
                    });
                    break;
                default:
                    alert("Erro desconhecido: " + status);
            }
        }
    });
}