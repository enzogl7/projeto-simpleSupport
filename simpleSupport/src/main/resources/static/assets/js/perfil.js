document.getElementById('edicaoTelefoneUsuario').addEventListener('input', function (e) {
    let telefone = e.target.value.replace(/\D/g, '');

    if (telefone.length > 10) {
        telefone = telefone.replace(/^(\d{2})(\d{5})(\d{4}).*/, '($1) $2-$3');
    } else {
        telefone = telefone.replace(/^(\d{2})(\d{4})(\d{0,4}).*/, '($1) $2-$3');
    }

    e.target.value = telefone;
});

function modalAlterarInformacoes() {
    $('#modalAlterarInformacoes').modal('show');
}

function editarPerfil() {
    var idUsuario = document.getElementById('idUsuario').value;
    var edicaoNomeUsuario = document.getElementById('edicaoNomeUsuario').value;
    var edicaoSobrenomeUsuario = document.getElementById('edicaoSobrenomeUsuario').value;
    var edicaoEmailUsuario = document.getElementById('edicaoEmailUsuario').value;
    var edicaoTelefoneUsuario = document.getElementById('edicaoTelefoneUsuario').value;

    if (!edicaoNomeUsuario && !edicaoSobrenomeUsuario && !edicaoEmailUsuario && !edicaoTelefoneUsuario) {
        Swal.fire({
            title: "Ops!",
            text: "Pelo menos um campo deve ser preenchido!",
            icon: "warning",
            confirmButtonText: 'OK'
        })
        return;
    }

    $.ajax({
        url: '/perfil/salvaredicao',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            idUsuario: idUsuario,
            nome: edicaoNomeUsuario,
            sobrenome: edicaoSobrenomeUsuario,
            email: edicaoEmailUsuario,
            telefone: edicaoTelefoneUsuario
        }),
        complete: function(xhr, status) {
            switch (xhr.status) {
                case 200:
                    Swal.fire({
                        title: "Pronto!",
                        text: "Perfil editado com sucesso!",
                        icon: "success",
                        confirmButtonText: 'OK'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            location.reload();
                        }
                    });
                    break;
                case 500:
                    Swal.fire({
                        title: "Erro!",
                        text: "Ocorreu um erro ao editar o perfil.",
                        icon: "error"
                    });
                    break;
                default:
                    alert("Erro desconhecido: " + status);
            }
        }
    });
}