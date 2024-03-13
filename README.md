Service interface:
- `POST /stub-requests/{tag}/` - save the request and its body with request timestamp.
- `GET /stub-requests/?tag=something&page=0&size=20` - receive requests(pageable). `tag` is optional.
- `DELETE /stub-requests/?ids=1,2,3` - delete records by `IDs`.
