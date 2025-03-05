function modalEmpresa() {
    $('#modalEmpresa').modal('show');
}

document.getElementById('edicaoCnpjEmpresa').addEventListener('input', function() {
    var cnpj = this.value;
    this.value = formatCNPJ(cnpj);
});

function editarEmpresa() {
    var idEmpresa = document.getElementById('idEmpresa').value;
    var nomeEmpresa = document.getElementById('edicaoNomeEmpresa').value;
    var cnpj = document.getElementById('edicaoCnpjEmpresa').value;
    var razaoSocial = document.getElementById('edicaoRazaoSocialEmpresa').value;
    var emailEmpresa = document.getElementById('edicaoEmailEmpresa').value;
    var emailResponsavelEmpresa = document.getElementById('edicaoEmailResponsavelEmpresa').value;

    if (!idEmpresa || !nomeEmpresa || !cnpj || !razaoSocial || !emailEmpresa || !emailResponsavelEmpresa) {
        Swal.fire({
            title: "Ops!",
            text: "Preencha todos os campos!",
            icon: "warning",
            confirmButtonText: 'OK'
        })
        return;
    }

    $.ajax({
        url: '/empresa/salvaredicao',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            idEmpresa: idEmpresa,
            nomeEmpresa: nomeEmpresa,
            cnpj: cnpj,
            razaoSocial: razaoSocial,
            emailEmpresa: emailEmpresa,
            emailResponsavelEmpresa: emailResponsavelEmpresa
        }),
        complete: function(xhr, status) {
            switch (xhr.status) {
                case 200:
                    Swal.fire({
                        title: "Pronto!",
                        text: "Empresa editada com sucesso!",
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
                        text: "Não existe um usuário com esse email informado para ser o responsável pela empresa.",
                        icon: "warning",
                        confirmButtonText: "OK"
                    })
                    break;
                case 500:
                    Swal.fire({
                        title: "Erro!",
                        text: "Ocorreu um erro ao editar essa empresa.",
                        icon: "error"
                    });
                    break;
                default:
                    alert("Erro desconhecido: " + status);
            }
        }
    });
}